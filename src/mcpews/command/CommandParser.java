/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.command;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import mcpews.MCSocketServer;
import mcpews.logger.LogLevel;
import mcpews.message.MCCommand;

/**
 * This class is only intended to parse user input. If you want to create
 * MCCommand objects, please create them directly rather than use this class.
 *
 * @author Jocopa3
 */
public class CommandParser {

    private MCSocketServer server;

    public CommandParser(MCSocketServer server) {
        this.server = server;
    }

    /* 
     * Splits a string by spaces, but ignores spaces when inside brackets or quotes
     * This is meant to be faster and less error-prone than using regex.
     * 
     * For example, the string: @a[name=Monkey Wrench, m = -1 ] "Super User" one two
     * 
     * returns an array of strings containing:
     * 
     * @a[name=Monkey Wrench, m = -1 ]
     * "Super User"
     * one
     * two
     */
    private String[] splitCommandText(String commandText) {
        commandText = commandText.trim();

        int length = commandText.length();
        ArrayList<String> parameters = new ArrayList<>();

        StringBuilder currentString = new StringBuilder();
        int inBrackets = 0;
        boolean inQuotes = false;

        for (int i = 0; i < length; i++) {
            char c = commandText.charAt(i);

            switch (c) {
                case '"':
                    inQuotes = !inQuotes;
                    break;
                case '[':
                    inBrackets++;
                    break;
                case ']':
                    inBrackets--;
                    break;
            }

            if (inBrackets == 0 && !inQuotes && c == ' ') {
                parameters.add(currentString.toString());
                currentString.delete(0, currentString.length());
            } else {
                currentString.append(c);

                if (i == length - 1) {
                    parameters.add(currentString.toString());
                }
            }
        }

        return parameters.toArray(new String[0]);
    }

    /*
     * Attempts to parse command text into an MCCommand object
     * The input string must follow the same conventions as normal Minecraft commands
     * For more info about commands, see: https://minecraft.gamepedia.com/Commands
     *
     * Examples of valid input strings:
     *
     * /kill @e[type=!sheep,r=20]
     * /time set day
     * /enchant Jocopa3 sharpness 5
     * /execute @a ~ ~ ~ summon lightning ~ ~ ~
     */
    // Ugliest, function, ever.
    // This method is horridly slow; please only use it when the user types in commands and nothing else
    public MCCommand parseCommand(String input) {
        if (!input.startsWith("/")) {
            return null;
        }

        CommandType command = CommandType.fromChatString(input);

        if (command == null) {
            server.getLog().log(LogLevel.INFO, "Unknown command: {0}", input);
            return null;
        }

        // Parameters should store all string parameters following the /command input
        // Possible bug when typing a space between '/' and the command name like: / summon tnt
        String[] parameters = splitCommandText(input.replaceFirst("/" + command.getName(), ""));
        System.out.println("Parsing command: " + input);
        System.out.println("Command parameters: \n" + Arrays.toString(parameters));
        MCCommand commandRequest = null;

        Class commandClass = command.getRequestClass();

        if (parameters.length == 0) {
            try {
                Constructor constructor = commandClass.getConstructor();
                commandRequest = (MCCommand) constructor.newInstance();
            } catch (Exception e) {
            }
        }

        // Get all constructors and loop through them
        // Try to initialize the command object using each constructor
        Constructor[] constructors = commandClass.getConstructors();

        mainLoop:
        for (Constructor constructor : constructors) {
            Class[] commandParams = constructor.getParameterTypes();

            if (commandParams.length == 1 && commandParams[0].getSuperclass().equals(CommandInput.class)) {
                Constructor[] inputConstructors = commandParams[0].getConstructors();

                for (Constructor inputConstructor : inputConstructors) {
                    Class[] inputParams = inputConstructor.getParameterTypes();
                    int paramIndx = 0, objIndx = 0;
                    Object[] objects = new Object[inputParams.length];

                    // Try to create each object in the constructor by passing the parameter strings
                    // This is a horrid hack and should be replaced...
                    try {
                        for (Class paramType : inputParams) {
                            Constructor paramCtor = null;
                            String[] paramList = null;

                            if (paramType.isArray()) {
                                Object param = Array.newInstance(paramType.getComponentType(), parameters.length - paramIndx);
                                int len = Array.getLength(param);
                                Constructor componentConstructor = paramType.getComponentType().getConstructor(String.class);
                                
                                for (int i = 0; i < len; i++) {
                                    Object obj = componentConstructor.newInstance(parameters[paramIndx + i]);
                                    Array.set(param, i, obj);
                                }
                                
                                objects[objIndx] = param;
                                objIndx++;
                                paramIndx += len;
                            } else {
                                int i;
                                for (i = 1; i <= parameters.length; i++) {
                                    try {
                                        Class[] constructorObjects = new Class[i];
                                        Arrays.fill(constructorObjects, String.class);

                                        paramCtor = paramType.getConstructor(constructorObjects);
                                        if (paramCtor != null) {
                                            paramList = new String[i];
                                            System.arraycopy(parameters, paramIndx, paramList, 0, i);
                                            break;
                                        }
                                    } catch (Exception e) {
                                    }
                                }

                                if (paramCtor != null && paramList != null) {
                                    try {
                                        objects[objIndx] = paramCtor.newInstance((Object[]) paramList);
                                    } catch (Exception e) {
                                        break;
                                    }

                                    objIndx++;
                                    paramIndx += i;
                                } else {
                                    break;
                                }
                            }
                        }

                        // Check if all the parameters the user typed were used
                        if (paramIndx != parameters.length) {
                            continue;
                        }

                        CommandInput commandInput = (CommandInput) inputConstructor.newInstance(objects);
                        commandRequest = (MCCommand) constructor.newInstance(commandInput);
                        break mainLoop;
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }

        // Inform the user if the command couldn't be parsed
        if (commandRequest == null) {
            server.getLog().log(LogLevel.INFO, "Couldn't parse command: " + input);
        }

        return commandRequest;
    }
}

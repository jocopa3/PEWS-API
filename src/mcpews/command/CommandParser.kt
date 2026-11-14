package mcpews.command

import java.lang.reflect.Array as ReflectArray
import java.lang.reflect.Constructor
import java.util.ArrayList
import java.util.Arrays
import java.util.logging.Level
import mcpews.MCSocketServer
import mcpews.message.MCCommand

/**
 * This class is only intended to parse user input. If you want to create
 * MCCommand objects, please create them directly rather than use this class.
 *
 * @author Jocopa3
 */
open class CommandParser(private val server: MCSocketServer?) {

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
    private fun splitCommandText(commandText: String): Array<String> {
        val text = commandText.trim { it <= ' ' }

        val length = text.length
        val parameters = ArrayList<String>()

        val currentString = StringBuilder()
        var inBrackets = 0
        var inQuotes = false

        for (i in 0 until length) {
            val c = text[i]

            when (c) {
                '"' -> inQuotes = !inQuotes
                '[' -> inBrackets++
                ']' -> inBrackets--
            }

            if (inBrackets == 0 && !inQuotes && c == ' ') {
                parameters.add(currentString.toString())
                currentString.delete(0, currentString.length)
            } else {
                currentString.append(c)

                if (i == length - 1) {
                    parameters.add(currentString.toString())
                }
            }
        }

        return parameters.toTypedArray()
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
    open fun parseCommand(input: String): MCCommand? {
        if (!input.startsWith("/")) {
            return null
        }

        val command = CommandType.fromChatString(input)

        if (command == null) {
            MCSocketServer.getLog().log(Level.INFO, "Unknown command: {0}", input)
            return null
        }

        // Parameters should store all string parameters following the /command input
        // Possible bug when typing a space between '/' and the command name like: / summon tnt
        val parameters = splitCommandText(Regex("/" + command.getName()).replaceFirst(input, ""))
        System.out.println("Parsing command: " + input)
        System.out.println("Command parameters: \n" + Arrays.toString(parameters))
        var commandRequest: MCCommand? = null

        val commandClass = command.getRequestClass()

        if (parameters.isEmpty()) {
            try {
                val constructor = commandClass.getConstructor()
                commandRequest = constructor.newInstance() as MCCommand
            } catch (e: Exception) {
            }
        }

        // Get all constructors and loop through them
        // Try to initialize the command object using each constructor
        val constructors = commandClass.getConstructors()

        mainLoop@ for (constructor in constructors) {
            val commandParams = constructor.getParameterTypes()

            if (commandParams.size == 1 && commandParams[0].getSuperclass().equals(CommandInput::class.java)) {
                val inputConstructors = commandParams[0].getConstructors()

                for (inputConstructor in inputConstructors) {
                    val inputParams = inputConstructor.getParameterTypes()
                    var paramIndx = 0
                    var objIndx = 0
                    val objects = arrayOfNulls<Any>(inputParams.size)

                    // Try to create each object in the constructor by passing the parameter strings
                    // This is a horrid hack and should be replaced...
                    try {
                        for (paramType in inputParams) {
                            var paramCtor: Constructor<*>? = null
                            var paramList: Array<String?>? = null

                            if (paramType.isArray) {
                                val param = ReflectArray.newInstance(paramType.componentType, parameters.size - paramIndx)
                                val len = ReflectArray.getLength(param)
                                val componentConstructor = paramType.componentType.getConstructor(String::class.java)

                                for (i in 0 until len) {
                                    val obj = componentConstructor.newInstance(parameters[paramIndx + i])
                                    ReflectArray.set(param, i, obj)
                                }

                                objects[objIndx] = param
                                objIndx++
                                paramIndx += len
                            } else {
                                var i = 1
                                while (i <= parameters.size) {
                                    try {
                                        val constructorObjects = arrayOfNulls<Class<*>>(i)
                                        Arrays.fill(constructorObjects, String::class.java)

                                        paramCtor = paramType.getConstructor(*constructorObjects)
                                        if (paramCtor != null) {
                                            paramList = arrayOfNulls<String>(i)
                                            System.arraycopy(parameters, paramIndx, paramList, 0, i)
                                            break
                                        }
                                    } catch (e: Exception) {
                                    }
                                    i++
                                }

                                if (paramCtor != null && paramList != null) {
                                    try {
                                        objects[objIndx] = paramCtor.newInstance(*paramList)
                                    } catch (e: Exception) {
                                        break
                                    }

                                    objIndx++
                                    paramIndx += i
                                } else {
                                    break
                                }
                            }
                        }

                        // Check if all the parameters the user typed were used
                        if (paramIndx != parameters.size) {
                            continue
                        }

                        val commandInput = inputConstructor.newInstance(*objects) as CommandInput
                        commandRequest = constructor.newInstance(commandInput) as MCCommand
                        break@mainLoop
                    } catch (e: Exception) {
                        continue
                    }
                }
            }
        }

        // Inform the user if the command couldn't be parsed
        if (commandRequest == null) {
            MCSocketServer.getLog().log(Level.INFO, "Couldn't parse command: $input")
        }

        return commandRequest
    }
}

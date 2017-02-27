/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.command;

import java.util.HashMap;
import mcpews.message.MCResponse;
import mcpews.response.*;

/**
 *
 * @author Jocopa3
 */
public enum CommandType {
    // Command classes must not be null!

    CLOSE_WEBSOCKET("closewebsocket", CloseWebSocketCommand.class, CloseWebSocketResponse.class),
    CONNECT("connect", WSServerCommand.class, WSServerResponse.class),
    LIST("list", ListCommand.class, ListResponse.class),
    LISTD("listd", ListDetailCommand.class, ListDetailResponse.class),
    SAY("say", SayCommand.class, SayResponse.class),
    SETBLOCK("setblock", SetBlockCommand.class, SetBlockResponse.class),
    SUMMON("summon", SummonCommand.class, SummonResponse.class),
    TESTFORBLOCK("testforblock", TestForBlockCommand.class, TestForBlockResponse.class),
    TIME_ADD("time add", TimeAddCommand.class, TimeResponse.class),
    TIME_QUERY("time query", TimeQueryCommand.class, TimeResponse.class),
    TIME_SET("time set", TimeSetCommand.class, TimeResponse.class),
    WSSERVER("wsserver", WSServerCommand.class, WSServerResponse.class);

    private String name;
    private Class requestBodyClass;
    private Class responseBodyClass;
    
    private static HashMap<String, CommandType> commands;
    static {
        commands = new HashMap<>();
        for (CommandType command : values()) {
            commands.put(command.getName(), command);
        }
    }

    CommandType(String name, Class requestClass, Class responseClass) {
        this.name = name;
        requestBodyClass = requestClass;
        responseBodyClass = responseClass;
    }

    public String getName() {
        return name;
    }

    public Class getRequestClass() {
        return requestBodyClass;
    }

    public Class getResponseClass() {
        return responseBodyClass;
    }

    public static CommandType fromString(String name) {
        return commands.get(name);
    }

    public static CommandType fromChatString(String chat) {
        if (chat.startsWith("/")) {
            chat = chat.replaceFirst("/", "");
        }
        
        for (CommandType type : values()) {
            if (chat.startsWith(type.getName())) {
                return type;
            }
        }

        return null;
    }
}

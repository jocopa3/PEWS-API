/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.command;

import java.util.HashMap;
import mcpews.response.*;
/**
 *
 * @author Jocopa3
 */
public enum CommandType {
    // Command classes must not be null! (Except closewebsocket)
    
    CLOSE_WEBSOCKET("closewebsocket", CloseWebSocketCommand.class, null),
    LIST("list", ListCommand.class, ListResponse.class),
    LISTD("listd", ListDetailCommand.class, ListDetailResponse.class),
    SAY("say", SayCommand.class, SayResponse.class);
    
    private String name;
    private Class requestBodyClass;
    private Class responseBodyClass;
    private static HashMap<String, CommandType> commands = new HashMap<>();
    
    static {
        for(CommandType command : values()) {
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
}

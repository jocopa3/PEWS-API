/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.message;

import mcpews.command.*;

/**
 *
 * @author Jocopa3
 */
public class MCResponse extends MCBody {
    
    protected int statusCode;
    protected String statusMessage; // This may be null
    protected transient CommandType type;
    
    protected MCResponse() {
        super(MessagePurposeType.COMMAND_RESPONSE);
    }
    
    protected void setCommandType(CommandType type) {
        this.type = type;
    }
    
    public CommandType getResponseType() {
        return type;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public String getStatusMessage() {
        return statusMessage;
    }
}

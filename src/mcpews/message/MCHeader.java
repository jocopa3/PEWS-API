/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.message;

import java.util.UUID;
import mcpews.message.MessagePurposeType;

/**
 *
 * @author Jocopa3
 */
public class MCHeader {
    protected int version;
    protected String requestId;
    protected String messagePurpose;
    protected String messageType;
    
    public MCHeader(int version, UUID requestId, MessagePurposeType purpose) {
        this.version = version;
        this.requestId = requestId.toString();
        messagePurpose = purpose.getPurposeName();
    }
    
    public MCHeader(int version, UUID requestId, MessagePurposeType purpose, MessagePurposeType type) {
        this(version, requestId, purpose);
        messageType = type.getPurposeName();
    }
    
    public UUID getRequestId() {
        return UUID.fromString(requestId);
    }
    
    public MessagePurposeType getPurpose() {
        return MessagePurposeType.fromString(messagePurpose);
    }
    
    public MessagePurposeType getType() {
        return MessagePurposeType.fromString(messageType);
    }
    
    public int getVersion() {
        return version;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.message;

/**
 *
 * @author Jocopa3
 */
public abstract class MCBody {

    private transient MCMessage parentMessage;
    private transient MessagePurposeType purpose;

    public MCBody(MessagePurposeType purpose) {
        this.purpose = purpose;
        parentMessage = new MCMessage(this);
    }

    protected MessagePurposeType getPurpose() {
        return purpose;
    }

    public MCMessage getAsMessage() {
        return parentMessage;
    }
}

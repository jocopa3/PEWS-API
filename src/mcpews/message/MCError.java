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
public class MCError extends MCBody {

    String statusMessage;
    int statusCode;

    protected MCError() {
        super(MessagePurposeType.ERROR);
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String toString() {
        return "Error (" + statusCode + "): " + statusMessage;
    }
}

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
public enum StatusCodes {
    SUCCESS(0),
    PROCESSING_COMMAND(131075),
    TOO_MANY_REQUESTS(-2147418109);
    
    int value;
    
    StatusCodes(int code) {
        value = code;
    }
}

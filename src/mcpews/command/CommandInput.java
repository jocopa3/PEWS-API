/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.command;

/**
 *
 * @author Jocopa3
 */
public abstract class CommandInput {
    transient String overloadName = "default";
    
    public String getOverload() {
        return overloadName;
    }
}

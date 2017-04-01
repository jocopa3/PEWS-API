/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mcpews.util;

/**
 *
 * @author Jocopa3
 */
public class StringArray {
    String[] arr;
    
    public StringArray(String... strings) {
        arr = strings;
    }
    
    @Override
    public String toString() {
        return String.join(" ", arr);
    }
}

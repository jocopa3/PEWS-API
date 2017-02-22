
package mcpews.message;

import mcpews.MCListener;

/**
 *
 * @author Jocopa3
 */
public class ListenerRequest {
    private MCListener requestor;
    private MCMessage request;
    
    public ListenerRequest(MCListener listener, MCMessage request) {
        requestor = listener;
        this.request = request;
    }
    
    public MCListener getRequestor() {
        return requestor;
    }
    
    public MCMessage getRequestMessage() {
        return request;
    }
}

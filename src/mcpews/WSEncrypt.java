package mcpews;

import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.handshake.*;

public class WSEncrypt extends Draft_17 {

    public static final String WSENCRYPT_PROTOCOL_NAME = "com.microsoft.minecraft.wsencrypt";

    @Override
    public HandshakeState acceptHandshakeAsServer(ClientHandshake handshakedata) throws InvalidHandshakeException {
        if (super.acceptHandshakeAsServer(handshakedata) == HandshakeState.NOT_MATCHED) {
            return HandshakeState.NOT_MATCHED;
        }

        if (!handshakedata.hasFieldValue("Sec-WebSocket-Protocol")) {
            return HandshakeState.NOT_MATCHED;
        }

        if (handshakedata.getFieldValue("Sec-WebSocket-Protocol").equalsIgnoreCase(WSENCRYPT_PROTOCOL_NAME)) {
            return HandshakeState.MATCHED;
        } else {
            return HandshakeState.NOT_MATCHED;
        }
    }

    @Override
    public HandshakeState acceptHandshakeAsClient(ClientHandshake request, ServerHandshake response) throws InvalidHandshakeException {
        if (super.acceptHandshakeAsClient(request, response) == HandshakeState.NOT_MATCHED) {
            return HandshakeState.NOT_MATCHED;
        }

        if (!request.hasFieldValue("Sec-WebSocket-Protocol")) {
            return HandshakeState.NOT_MATCHED;
        }

        if (request.getFieldValue("Sec-WebSocket-Protocol").equalsIgnoreCase(WSENCRYPT_PROTOCOL_NAME)) {
            return HandshakeState.MATCHED;
        }
        return HandshakeState.NOT_MATCHED;
    }

    @Override
    public ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder request) {
        super.postProcessHandshakeRequestAsClient(request);
        request.put("Sec-WebSocket-Protocol", WSENCRYPT_PROTOCOL_NAME);
        return request;
    }

    @Override
    public HandshakeBuilder postProcessHandshakeResponseAsServer(ClientHandshake request, ServerHandshakeBuilder response) throws InvalidHandshakeException {
        HandshakeBuilder result = super.postProcessHandshakeResponseAsServer(request, response);
        result.put("Sec-WebSocket-Protocol", WSENCRYPT_PROTOCOL_NAME);
        return result;
    }

    @Override
    public Draft copyInstance() {
        return new WSEncrypt();
    }
}

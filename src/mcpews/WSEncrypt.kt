package mcpews

import org.java_websocket.drafts.Draft
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.enums.HandshakeState
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.handshake.ClientHandshakeBuilder
import org.java_websocket.handshake.HandshakeBuilder
import org.java_websocket.handshake.ServerHandshake
import org.java_websocket.handshake.ServerHandshakeBuilder

/**
 * PEWS 的 wsencrypt 握手协议。
 *
 * 上游源码针对旧版 Java-WebSocket 编写；这里只做 API 兼容适配：
 * HandshakeState 从 Draft 内部类迁移到了 enums 包，相关 override 允许抛出
 * InvalidHandshakeException。
 */
open class WSEncrypt : Draft_6455() {

    companion object {
        const val WSENCRYPT_PROTOCOL_NAME: String = "com.microsoft.minecraft.wsencrypt"
    }

    override fun acceptHandshakeAsServer(handshakedata: ClientHandshake): HandshakeState {
        if (super.acceptHandshakeAsServer(handshakedata) == HandshakeState.NOT_MATCHED) {
            return HandshakeState.NOT_MATCHED
        }

        if (!handshakedata.hasFieldValue("Sec-WebSocket-Protocol")) {
            return HandshakeState.NOT_MATCHED
        }

        return if (handshakedata.getFieldValue("Sec-WebSocket-Protocol")
                .equals(WSENCRYPT_PROTOCOL_NAME, ignoreCase = true)) {
            HandshakeState.MATCHED
        } else {
            HandshakeState.NOT_MATCHED
        }
    }

    override fun acceptHandshakeAsClient(request: ClientHandshake, response: ServerHandshake): HandshakeState {
        if (super.acceptHandshakeAsClient(request, response) == HandshakeState.NOT_MATCHED) {
            return HandshakeState.NOT_MATCHED
        }

        if (!request.hasFieldValue("Sec-WebSocket-Protocol")) {
            return HandshakeState.NOT_MATCHED
        }

        return if (request.getFieldValue("Sec-WebSocket-Protocol")
                .equals(WSENCRYPT_PROTOCOL_NAME, ignoreCase = true)) {
            HandshakeState.MATCHED
        } else {
            HandshakeState.NOT_MATCHED
        }
    }

    override fun postProcessHandshakeRequestAsClient(request: ClientHandshakeBuilder): ClientHandshakeBuilder {
        super.postProcessHandshakeRequestAsClient(request)
        request.put("Sec-WebSocket-Protocol", WSENCRYPT_PROTOCOL_NAME)
        return request
    }

    override fun postProcessHandshakeResponseAsServer(
        request: ClientHandshake,
        response: ServerHandshakeBuilder
    ): HandshakeBuilder {
        val result = super.postProcessHandshakeResponseAsServer(request, response)
        result.put("Sec-WebSocket-Protocol", WSENCRYPT_PROTOCOL_NAME)
        return result
    }

    override fun copyInstance(): Draft = WSEncrypt()
}

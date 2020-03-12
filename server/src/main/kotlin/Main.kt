import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress
import java.util.concurrent.atomic.AtomicInteger

class ChatServer(port: Int) : WebSocketServer(InetSocketAddress(port)) {

    private var nextId = AtomicInteger()

    override fun onOpen(connection: WebSocket, handshake: ClientHandshake?) {
        val id = nextId.incrementAndGet()

        val message = Message(id, "connected")

        println("${message.userId} connects")
        sendToAll(message)
        connection.setAttachment(id)
    }

    override fun onClose(connection: WebSocket, code: Int, reason: String?, remote: Boolean) {
        val message = Message(connection.getAttachment(), "disconnected")
        println("${message.userId} disconnects")
        sendToAll(message)
    }

    override fun onMessage(connection: WebSocket, message: String) {
        val toClientMessage = Message(connection.getAttachment(), message)

        sendToAll(toClientMessage)
    }

    override fun onStart() {
        println("server starts on port $port")
    }

    override fun onError(connection: WebSocket, e: Exception?) {
        println("error: $e")
    }

    private fun sendToAll(message: Message) {
        val string = stringifyMessage(message)

        println("sending to all: $string")

        connections.filter(WebSocket::isOpen).forEach { openedConnection ->
            try {
                openedConnection.send(string)
            } catch (t: Throwable) {
            }
        }
    }
}

object Main {

    @JvmStatic
    fun main(vararg args: String) {
        val server = ChatServer(DEFAULT_PORT).apply {
            isReuseAddr = true

            start()
        }

        Runtime.getRuntime().addShutdownHook(object : Thread() {

            override fun run() {
                server.stop()
            }
        })
    }
}

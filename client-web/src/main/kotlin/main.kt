import org.w3c.dom.*
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.url.URL
import kotlin.browser.document
import kotlin.browser.window

class ChatClient {

    private val inputMessage = document.getElementById("input-message") as HTMLInputElement
    private val messagesDiv = document.getElementById("messages") as HTMLDivElement

    private val ws = WebSocket("ws://$HOST:$PORT").apply {
        onopen = fun(_: Event) {
            messagesDiv.innerHTML = "Connected!<br/>" + messagesDiv.innerHTML
        }

        onclose = fun(event: Event) {
            event as CloseEvent

            messagesDiv.innerHTML = "Disconnected... ${event.reason}"
        }

        onmessage = fun(event: MessageEvent) {
            val messageData = event.data as String

            when (val encodedMessage = parseMessage(messageData)) {
                is Message -> {
                    messagesDiv.innerHTML = "${encodedMessage.userId} - ${encodedMessage.message}<br/>" + messagesDiv.innerHTML
                }

                is ConnectionChange -> {
                    val connectionText = when (encodedMessage.connects) {
                        true -> "connected"

                        false -> "disconnected"
                    }

                    messagesDiv.innerHTML = "${encodedMessage.userId} $connectionText<br/>" + messagesDiv.innerHTML
                }
            }
        }
    }

    private fun onEnter(event: Event) {
        require(event is KeyboardEvent)

        if (event.key == "Enter") {
            ws.send(inputMessage.value)

            inputMessage.value = ""
        }
    }

    init {
        inputMessage.addEventListener("keyup", ::onEnter)
    }

    companion object {

        private const val DEFAULT_HOST = "localhost"

        private val HOST: String
        private val PORT: Int

        init {
            with(URL(window.location.href).searchParams) {
                HOST = get("host") ?: DEFAULT_HOST
                PORT = get("port")?.toIntOrNull() ?: DEFAULT_PORT
            }
        }
    }
}

fun main() {
    ChatClient()
}

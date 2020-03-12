import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

@Serializable
data class Message(
        @SerialName("id")
        val userId: Int,
        val message: String
)

private val json = Json(JsonConfiguration.Stable)

fun parseMessage(message: String) = json.parse(Message.serializer(), message)

fun stringifyMessage(message: Message) = json.stringify(Message.serializer(), message)

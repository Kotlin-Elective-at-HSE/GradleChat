import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

@Serializable
sealed class ServerEvent

@Serializable
data class Message(
        @SerialName("id")
        val userId: Int,
        val message: String
) : ServerEvent()

private val json = Json(JsonConfiguration.Stable)

fun parseMessage(message: String) = json.parse(ServerEvent.serializer(), message)

fun stringifyMessage(message: ServerEvent) = json.stringify(ServerEvent.serializer(), message)

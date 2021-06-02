package payload.response

import kotlinx.serialization.Serializable

@Serializable
data class Messages(
	val messages: List<Chats.Chat.Message>
)

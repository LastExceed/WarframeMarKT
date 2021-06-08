package payload.response

import kotlinx.serialization.Serializable

@Serializable
data class Messages private constructor(
	val messages: List<Chats.Chat.Message>
)

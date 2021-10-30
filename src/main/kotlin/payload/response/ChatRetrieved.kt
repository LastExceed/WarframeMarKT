package payload.response

import enums.Id
import kotlinx.serialization.Serializable

@Serializable
data class ChatRetrieved private constructor(
	val chat_id: Id<Chats.Chat>
)

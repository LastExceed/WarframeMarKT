package payload.response

import enums.IdChat
import kotlinx.serialization.Serializable

@Serializable
data class ChatRetrieved(
	val chat_id: IdChat
)

package payload.response

import enums.IdChat
import kotlinx.serialization.Serializable

@Serializable
data class ChatRetrieved private constructor(
	val chat_id: IdChat
)

package payload.response

import enums.IdChat
import kotlinx.serialization.Serializable

@Serializable
data class ChatCreated(val chat_id: IdChat)

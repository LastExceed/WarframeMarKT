package payload.response

import enums.*
import kotlinx.serialization.Serializable

@Serializable
data class ChatCreated(val chat_id: IdChat)

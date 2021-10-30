package payload.request

import enums.*
import kotlinx.serialization.Serializable
import payload.response.Chats
import payload.response.common.User

@Serializable
data class IgnoreCreate(
	val user_id: Id<User>,
	val chat_id: Id<Chats.Chat>
)
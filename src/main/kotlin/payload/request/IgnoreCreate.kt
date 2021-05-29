package payload.request

import enums.IdChat
import enums.IdUser
import kotlinx.serialization.Serializable

@Serializable
data class IgnoreCreate(
	val user_id: IdUser,
	val chat_id: IdChat
)
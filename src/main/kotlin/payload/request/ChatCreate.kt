package payload.request

import enums.Id
import kotlinx.serialization.Serializable
import payload.response.common.User

@Serializable
data class ChatCreate(
	val user_id: Id<User>
)

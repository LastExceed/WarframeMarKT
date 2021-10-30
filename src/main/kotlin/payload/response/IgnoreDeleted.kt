package payload.response

import enums.Id
import kotlinx.serialization.Serializable
import payload.response.common.User

@Serializable
data class IgnoreDeleted private constructor(
	val user_id: Id<User>
)
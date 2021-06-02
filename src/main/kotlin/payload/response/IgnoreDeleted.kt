package payload.response

import enums.IdUser
import kotlinx.serialization.Serializable

@Serializable
data class IgnoreDeleted private constructor(
	val user_id: IdUser
)
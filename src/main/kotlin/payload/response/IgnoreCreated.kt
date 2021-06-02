package payload.response

import payload.response.common.UserShort
import kotlinx.serialization.Serializable

@Serializable
data class IgnoreCreated private constructor(
	val user: UserShort
)
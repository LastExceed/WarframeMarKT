package payload.response

import payload.response.common.UserShortest
import kotlinx.serialization.Serializable

@Serializable
data class IgnoreCreated private constructor(
	val user: UserShortest
)
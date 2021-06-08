package payload.response

import payload.response.common.UserProfilePrivate
import kotlinx.serialization.Serializable

@Serializable
data class ProfilePrivate private constructor(
	val user: UserProfilePrivate
)

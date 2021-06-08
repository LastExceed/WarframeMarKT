package payload.response

import payload.response.common.UserProfilePrivate
import kotlinx.serialization.Serializable

@Serializable
data class ProfilePrivate(
	val user: UserProfilePrivate
)

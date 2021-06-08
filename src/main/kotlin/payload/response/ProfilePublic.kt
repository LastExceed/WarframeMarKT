package payload.response

import kotlinx.serialization.Serializable
import payload.response.common.*

@Serializable
data class ProfilePublic private constructor(
	val profile: UserProfilePublic
)
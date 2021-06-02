package payload.response

import payload.response.common.CurrentUser
import kotlinx.serialization.Serializable

@Serializable
data class ProfilePrivate(
	val user: CurrentUser
)

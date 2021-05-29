package payload.response

import payload.response.common.Self
import kotlinx.serialization.Serializable

@Serializable
data class ProfilePrivate(val user: Self)

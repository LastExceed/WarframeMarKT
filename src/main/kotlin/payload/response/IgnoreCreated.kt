package payload.response

import payload.response.common.User
import kotlinx.serialization.Serializable

@Serializable
data class IgnoreCreated private constructor(val user: User)
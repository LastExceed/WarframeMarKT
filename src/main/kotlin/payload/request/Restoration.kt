package payload.request

import kotlinx.serialization.Serializable

@Serializable
data class Restoration(val email: String)

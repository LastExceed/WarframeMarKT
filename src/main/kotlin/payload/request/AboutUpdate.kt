package payload.request

import kotlinx.serialization.Serializable

@Serializable
data class AboutUpdate(
	val about: String
)
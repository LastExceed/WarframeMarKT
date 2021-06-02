package payload.request

import kotlinx.serialization.Serializable

@Serializable
data class ReviewUpdate(
	val text: String
)
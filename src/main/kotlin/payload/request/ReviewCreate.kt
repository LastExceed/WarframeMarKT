package payload.request

import kotlinx.serialization.Serializable

@Serializable
data class ReviewCreate(
	val text: String,
	val review_type: Int
)
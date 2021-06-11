package payload.request

import enums.ReviewType
import kotlinx.serialization.Serializable

@Serializable
data class ReviewCreate(
	val text: String,
	val review_type: ReviewType
)
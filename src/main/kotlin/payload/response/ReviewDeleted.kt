package payload.response

import enums.IdReview
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDeleted private constructor(
	val deleted_id: IdReview,
	val decrease_reputation: Boolean
)
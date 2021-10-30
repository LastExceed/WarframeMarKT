package payload.response

import enums.Id
import kotlinx.serialization.Serializable
import payload.response.common.Review

@Serializable
data class ReviewDeleted private constructor(
	val deleted_id: Id<Review>,
	val decrease_reputation: Boolean
)
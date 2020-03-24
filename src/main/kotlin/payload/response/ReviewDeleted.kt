package payload.response

import enums.IdReview

data class ReviewDeleted private constructor(
	val deleted_id: IdReview,
	val decrease_reptuation: Boolean
)
package payload.response

data class ReviewDeleted private constructor(
	val deleted_id: String,
	val decrease_reptuation: Boolean
)
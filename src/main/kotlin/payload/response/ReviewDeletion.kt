package payload.response

data class ReviewDeletion private constructor(
	val deleted_id: String,
	val decrease_reptuation: Boolean
)
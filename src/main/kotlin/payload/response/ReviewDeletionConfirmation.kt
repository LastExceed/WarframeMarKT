package payload.response

data class ReviewDeletionConfirmation(
	val deleted_id: String,
	val decrease_reptuation: Boolean
)
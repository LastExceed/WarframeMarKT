package payload.request

data class ReviewCreation(
	val text: String,
	val review_type: Int
)
package payload.request

data class ReviewCreate(
	val text: String,
	val review_type: Int
)
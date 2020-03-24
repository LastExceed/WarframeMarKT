package payload.response

import payload.response.common.Review
import payload.response.common.Self

data class Reviews private constructor(
	val reviews: List<Review>,
	val total_review_count: Int,
	val own_review: Review?,
	val user: Self
)
package payload.response.common

import java.util.*

data class Review private constructor(
	val review_type: Int,
	val text: String,
	val hidden: Boolean,
	val date: Date,
	val user_from: User? = null, //null if own_review
	val id: String? = null //null if not own_review
)
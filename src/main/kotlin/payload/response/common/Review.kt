package payload.response.common

import kotlinx.datetime.*
import java.util.*
import kotlinx.serialization.Serializable

@Serializable
data class Review private constructor(
	val review_type: Int,
	val text: String,
	val hidden: Boolean,
	val date: Instant,
	val user_from: User? = null, //null if own_review
	val id: String? = null //null if not own_review
)
package payload.response.common

import enums.*
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Review private constructor(
	val review_type: ReviewType,
	val text: String,
	val hidden: Boolean,
	val date: Instant,
	val user_from: UserShortest? = null, //null if own_review
	val id: String? = null //null if not own_review
) : IdCarrier
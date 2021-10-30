package payload.response.common

import enums.*
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Review private constructor(
	override val id: Id<Review>,
	val review_type: ReviewType,
	val text: String,
	val hidden: Boolean,
	val date: Instant,
	val user_from: UserShortest? = null //null if own_review
) : IdCarrier
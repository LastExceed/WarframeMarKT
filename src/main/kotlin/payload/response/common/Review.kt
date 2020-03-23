package payload.response.common

data class Review private constructor(
	val review_type: Int,
	val text: String,
	val hidden: Boolean,
	val date: String,
	val user_from: User? = null, //null if own_review
	val id: String? = null //null if not own_review
) {
	data class User(
		val ingame_name: String,
		val avatar: String?,
		val id: String,
		val region: String,
		val reputation: Int,
		val status: String?
	)
}
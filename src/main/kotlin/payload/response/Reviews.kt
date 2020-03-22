package payload.response

data class Reviews(
	val reviews: List<Review>,
	val total_review_count: Int,
	val own_review: Review,
	val user: User
) {
	data class Review(
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

	data class User(
		val ingame_name: String,
		val role: String,
		val avatar: String?,
		val ban_reason: String?,
		val id: String,
		val banned: Boolean,
		val patreon_profile: String?,
		val reputation: Int,
		val region: String,
		val background: String?,
		val written_reviews: Int,
		val unread_messages: Int,
		val anonymous: Boolean,
		val has_mail: Boolean,
		val linked_accounts: Int,
		val check_code: String,
		val verification: Boolean,
		val platform: String,
		val ban_until: String?
	)
}
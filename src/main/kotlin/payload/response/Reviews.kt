package payload.response

import payload.response.common.Review

data class Reviews private constructor(
	val reviews: List<Review>,
	val total_review_count: Int,
	val own_review: Review,
	val user: User
) {
	data class User private constructor(
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
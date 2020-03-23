package payload.response

data class ProfilePrivate(val user: Profile) {
	data class Profile private constructor(
		val reputation: Int,
		val region: String,
		val check_code: String,
		val platform: String,
		val ban_reason: String?,
		val has_mail: Boolean,
		val role: String,
		val ingame_name: String,
		val anonymous: Boolean,
		val verification: Boolean,
		val unread_messages: Int,
		val id: String,
		val background: String?,
		val ban_until: String?,
		val banned: Boolean,
		val patreon_profile: String?,
		val linked_accounts: LinkedAccounts,
		val written_reviews: Int,
		val avatar: String?
	) {
		data class LinkedAccounts private constructor(
			val steam_profile: Boolean,
			val patreon_profile: Boolean,
			val xbox_profile: Boolean
		)
	}
}

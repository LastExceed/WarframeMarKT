package payload.response

import payload.response.common.Achievement

data class ProfilePublic private constructor(val profile: Profile) {
	data class Profile private constructor(
		val region: String,
		val about: String,
		val id: String,
		val background: String?,
		val own_profile: Boolean,
		val status: String,
		val avatar: String,
		val last_seen: String,
		val platform: String,
		val banned: Boolean,
		val achievements: List<Achievement>,
		val about_raw: String,
		val ingame_name: String,
		val reputation: Int
	)
}
package payload.response

import enums.*
import payload.response.common.Achievement
import payload.response.common.User
import java.util.*

data class ProfilePublic private constructor(val profile: Profile) {
	//TODO: inherit from User?
	data class Profile private constructor(
		val region: Region,
		val about: String,
		val id: IdUser,
		val background: String?,
		val own_profile: Boolean,
		val status: Status,
		val avatar: ResourceLocation,
		val last_seen: Date,
		val platform: Platform,
		val banned: Boolean,
		val achievements: List<Achievement>,
		val about_raw: String,
		val ingame_name: String,
		val reputation: Int
	)
}
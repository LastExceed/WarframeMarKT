package payload.response

import enums.*
import payload.response.common.Achievement
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ProfilePublic private constructor(
	val profile: Profile
) {
	@Serializable
	data class Profile private constructor(
		val region: Region,
		val about: String,
		val id: IdUser,
		val background: String?,
		val own_profile: Boolean,
		val status: Status,
		val avatar: ResourceLocation,
		val last_seen: Instant,
		val platform: Platform,
		val banned: Boolean,
		val achievements: List<Achievement>,
		val about_raw: String,
		val ingame_name: String,
		val reputation: Int
	)
}
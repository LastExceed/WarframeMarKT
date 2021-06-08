package payload.response.common

import enums.*
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class UserShort private constructor(
	var avatar: ResourceLocation?,
	var id: IdUser,
	var ingame_name: String,
	var last_seen: String,
	var region: Region,
	var reputation: Double,
	var status: Status? = null//missing in ignorecreate
)

@Serializable
data class UserProfilePublic private constructor(
	val avatar: ResourceLocation?,
	val background: String?,
	val id: IdUser,
	val ingame_name: String,
	val last_seen: Instant,
	val region: Region,
	val reputation: Int,
	val status: Status,

	val about: String,
	val about_raw: String,
	val achievements: List<Achievement>,
	val banned: Boolean,
	val own_profile: Boolean,
	val platform: Platform
)

@Serializable
data class UserProfilePrivate private constructor(
	val avatar: ResourceLocation? = null, //missing when anonymous
	val background: ResourceLocation? = null, //missing when non-existent
	val id: IdUser,
	val ingame_name: String? = null, //missing when anonymous
	//lastseen
	val region: Region,
	val reputation: Int,
	//status

	//about
	//about_raw
	//achievements
	val banned: Boolean,
	//own_profile
	val platform: Platform,

	val anonymous: Boolean,
	val ban_reason: String? = null, //missing when not banned
	val ban_until: Instant? = null, //missing when not banned
	val check_code: String?,
	val has_mail: Boolean,
	val linked_accounts: LinkedAccounts,
	val patreon_profile: PatreonProfile? = null, //missing when not a patreon
	val role: Role,
	val unread_messages: Int,
	val verification: Boolean,
	val written_reviews: Int
) {
	@Serializable
	data class LinkedAccounts private constructor(
		val steam_profile: Boolean,
		val patreon_profile: Boolean,
		val xbox_profile: Boolean
	)

	@Serializable
	data class PatreonProfile(
		val patreon_founder: Boolean,
		val subscription: Boolean,
		val patreon_badge: String
	)
}
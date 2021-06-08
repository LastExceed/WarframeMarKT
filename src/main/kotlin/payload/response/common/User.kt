package payload.response.common

import enums.*
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class UserShort private constructor(
	var status: Status? = null,//missing in ignorecreate
	var ingame_name: String,
	var region: Region,
	var avatar: ResourceLocation?,
	var id: IdUser,
	var reputation: Double,
	var last_seen: String
)

@Serializable
data class UserProfile private constructor(
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

@Serializable
data class CurrentUser private constructor(
	val reputation: Int,
	val region: Region,
	val check_code: String?,
	val platform: Platform,
	val ban_reason: String? = null, //missing when not banned
	val has_mail: Boolean,
	val role: Role,
	val ingame_name: String? = null, //missing in signout
	val anonymous: Boolean,
	val verification: Boolean,
	val unread_messages: Int,
	val id: IdUser,
	val background: ResourceLocation? = null, //missing when non-existent
	val ban_until: Instant? = null, //missing when not banned
	val banned: Boolean,
	val patreon_profile: PatreonProfile? = null, //missing when not a patreon
	val linked_accounts: LinkedAccounts,
	val written_reviews: Int,
	val avatar: ResourceLocation? = null //missing in signout
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
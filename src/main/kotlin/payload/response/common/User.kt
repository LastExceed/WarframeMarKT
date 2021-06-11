package payload.response.common

import enums.*
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

sealed interface User : IdCarrier {
	val avatar: ResourceLocation?
	val id: IdUser
	val ingame_name: String
	val region: Region
	val reputation: Float //sometimes has .0 at the end (server side bug)
}

sealed interface Profile {
	val background: ResourceLocation?
	val banned: Boolean
	val platform: Platform
}

sealed interface UserSeen : User {
	val last_seen: Instant
}

sealed interface UserTracked : UserSeen {
	val status: Status
}

@Serializable
data class UserShortest private constructor(
	override val avatar: ResourceLocation?,
	override val id: IdUser,
	override val ingame_name: String,
	override val last_seen: Instant,
	override val region: Region,
	override val reputation: Float
) : UserSeen

@Serializable
data class UserShort private constructor(
	override val avatar: ResourceLocation?,
	override val id: IdUser,
	override val ingame_name: String,
	override val last_seen: Instant,
	override val region: Region,
	override val reputation: Float,
	override val status: Status
) : UserTracked

@Serializable
data class UserProfilePublic private constructor(
	override val avatar: ResourceLocation?,
	override val id: IdUser,
	override val ingame_name: String,
	override val last_seen: Instant,
	override val region: Region,
	override val reputation: Float,
	override val status: Status,

	override val background: ResourceLocation? = null, //missing when non-existent
	override val banned: Boolean,
	override val platform: Platform,

	val about: String,
	val about_raw: String,
	val achievements: List<Achievement>,
	val own_profile: Boolean
) : UserTracked, Profile

@Serializable
data class UserProfilePrivate private constructor(
	override val avatar: ResourceLocation? = null, //missing when anonymous
	override val id: IdUser,
	override val ingame_name: String = "", //missing when anonymous
	override val region: Region,
	override val reputation: Float,

	override val background: ResourceLocation? = null, //missing when non-existent
	override val banned: Boolean,
	override val platform: Platform,

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
) : User, Profile {
	@Serializable
	data class LinkedAccounts private constructor(
		val steam_profile: Boolean,
		val patreon_profile: Boolean,
		val xbox_profile: Boolean,
		val discord_profile: Boolean
	)

	@Serializable
	data class PatreonProfile private constructor(
		val patreon_founder: Boolean,
		val subscription: Boolean,
		val patreon_badge: PatreonBadge
	)
}
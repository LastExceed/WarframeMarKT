package payload.response.common

import enums.*
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

sealed interface User {
	val avatar: ResourceLocation?
	val id: IdUser
	val ingame_name: String
	val region: Region
	val reputation: Int
}

sealed interface UserProfile {
	val background: ResourceLocation? //missing when non-existent
	val banned: Boolean
	val platform: Platform
}

sealed interface UserSeen : User {
	val last_seen: Instant
}

sealed interface UserPublic : UserSeen {
	val status: Status
}

@Serializable
data class UserShortest(
	override val avatar: ResourceLocation?,
	override val id: IdUser,
	override val ingame_name: String,
	override val last_seen: Instant,
	override val region: Region,
	override val reputation: Int
) : UserSeen

@Serializable
data class UserShort private constructor(
	override val avatar: ResourceLocation?,
	override val id: IdUser,
	override val ingame_name: String,
	override val last_seen: Instant,
	override val region: Region,
	override val reputation: Int,
	override val status: Status
) : UserPublic

@Serializable
data class UserProfilePublic private constructor(
	override val avatar: ResourceLocation?,
	override val id: IdUser,
	override val ingame_name: String,
	override val last_seen: Instant,
	override val region: Region,
	override val reputation: Int,
	override val status: Status,

	override val background: String?,
	override val banned: Boolean,
	override val platform: Platform,

	val about: String,
	val about_raw: String,
	val achievements: List<Achievement>,
	val own_profile: Boolean
) : UserPublic, UserProfile

@Serializable
data class UserProfilePrivate private constructor(
	override val avatar: ResourceLocation? = null, //missing when anonymous
	override val id: IdUser,
	override val ingame_name: String? = null, //missing when anonymous
	override val region: Region,
	override val reputation: Int,

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
) : User, UserProfile {
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
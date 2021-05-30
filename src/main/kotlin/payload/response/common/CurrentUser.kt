package payload.response.common

import enums.*
import kotlinx.datetime.*
import java.util.*
import kotlinx.serialization.Serializable

@Serializable
data class CurrentUser private constructor( //TODO: inherit from user ?
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
		val patreon_badge: String //TODO: enum
	)
}
package payload.response.common

import enums.*
import kotlinx.datetime.*
import java.util.*
import kotlinx.serialization.Serializable

@Serializable
data class Self private constructor(
	val reputation: Int,
	val region: Region,
	val check_code: String,
	val platform: Platform,
	val ban_reason: String?,
	val has_mail: Boolean,
	val role: Role,
	val ingame_name: String,
	val anonymous: Boolean,
	val verification: Boolean,
	val unread_messages: Int,
	val id: IdUser,
	val background: ResourceLocation?,
	val ban_until: Instant?,
	val banned: Boolean,
	val patreon_profile: String?,//unknown
	val linked_accounts: LinkedAccounts,
	val written_reviews: Int,
	val avatar: ResourceLocation?
) {
	@Serializable
	data class LinkedAccounts private constructor(
		val steam_profile: Boolean,
		val patreon_profile: Boolean,
		val xbox_profile: Boolean
	)
}
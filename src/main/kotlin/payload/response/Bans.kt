package payload.response

import enums.*
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import payload.response.common.UserShort

@Serializable
data class Bans(
	val bans: List<Ban>
) {
	@Serializable
	data class Ban(
		val auction: IdAuction,
		val user: UserShort,
		val reason: String,
		val created: Instant,
		val id: IdBan
	)
}

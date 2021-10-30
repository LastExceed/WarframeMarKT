package payload.response

import enums.*
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import payload.response.common.*

@Serializable
data class Bans private constructor(
	val bans: List<Ban>
) {
	@Serializable
	data class Ban private constructor(
		override val id: Id<Ban>,
		val auction: Id<Auction>,
		val user: UserShort,
		val reason: String,
		val created: Instant
	) : IdCarrier
}

package payload.response

import enums.*
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import payload.response.common.*

@Serializable
data class Bids private constructor(
	val bids: List<Bid>
) {
	@Serializable
	data class Bid private constructor(
		override val id: Id<Bid>,
		val value: Int,
		val created: Instant,
		val updated: Instant,
		val auction: Id<Auction>,
		val user: UserShort
	) : IdCarrier
}
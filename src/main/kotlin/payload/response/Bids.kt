package payload.response

import enums.*
import payload.response.common.UserShort
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Bids private constructor(
	val bids: List<Bid>
) {
	@Serializable
	data class Bid private constructor(
		override val id: IdBid,
		val value: Int,
		val created: Instant,
		val updated: Instant,
		val auction: IdAuction,
		val user: UserShort
	) : IdCarrier
}
package payload.response

import enums.IdAuction
import enums.IdBid
import kotlinx.datetime.*
import payload.response.common.UserShort
import kotlinx.serialization.Serializable

@Serializable
data class Bids private constructor(val bids: List<Bid>) {

	@Serializable
	data class Bid private constructor(
		val value: Int,
		val created: Instant,
		val updated: Instant,
		val auction: IdAuction,
		val user: UserShort,
		val id: IdBid
	)
}
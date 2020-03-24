package payload.response

import enums.IdAuction
import enums.IdBid
import payload.response.common.User
import java.util.*

data class Bids private constructor(val bids: List<Bid>) {
	data class Bid private constructor(
		val value: Int,
		val created: Date,
		val updated: Date,
		val auction: IdAuction,
		val user: User,
		val id: IdBid
	)
}
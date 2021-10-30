package payload.response

import enums.Id
import kotlinx.serialization.Serializable
import payload.response.common.Auction

@Serializable
data class AuctionClosed private constructor(
	val auction_id: Id<Auction>
)
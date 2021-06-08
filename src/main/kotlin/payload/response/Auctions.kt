package payload.response

import kotlinx.serialization.Serializable
import payload.response.common.*

@Serializable
data class Auctions<AuctionType : Auction> private constructor(
	val auctions: List<AuctionType>
)
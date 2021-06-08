package payload.response

import kotlinx.serialization.Serializable
import payload.response.common.*

@Serializable
data class AuctionRetrieved<AuctionType : Auction> private constructor(
	val auction: AuctionType
)
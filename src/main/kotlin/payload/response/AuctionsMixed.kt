package payload.response

import kotlinx.serialization.Serializable
import payload.response.common.*

@Serializable
data class AuctionsMixed private constructor(
	val auctions: List<AuctionEntryMixed>
)
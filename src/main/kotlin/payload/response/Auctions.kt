package payload.response

import kotlinx.serialization.Serializable
import payload.response.common.*

@Serializable
data class Auctions private constructor(
	val auctions: List<AuctionEntry>
)
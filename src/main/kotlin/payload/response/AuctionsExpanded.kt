package payload.response

import kotlinx.serialization.Serializable
import payload.response.common.*

@Serializable
data class AuctionsExpanded private constructor(
	val auctions: List<AuctionEntryExpanded>
)
package payload.response

import kotlinx.serialization.Serializable
import payload.response.common.AuctionEntry

@Serializable
data class AuctionCreated private constructor(
	val auction: AuctionEntry
)
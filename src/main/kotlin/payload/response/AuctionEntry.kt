package payload.response

import payload.response.common.Auction
import kotlinx.serialization.Serializable

@Serializable
data class AuctionEntry private constructor(val auction: Auction)
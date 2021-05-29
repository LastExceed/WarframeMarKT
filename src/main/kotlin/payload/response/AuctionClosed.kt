package payload.response

import enums.IdAuction
import kotlinx.serialization.Serializable

@Serializable
data class AuctionClosed private constructor(val auction_id: IdAuction)
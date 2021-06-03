package payload.response

import kotlinx.serialization.Serializable
import payload.response.common.*

@Serializable
data class AuctionRetrieved private constructor(
	val auction: AuctionEntryExpanded
)
package payload.response

import payload.response.common.Auction
import kotlinx.serialization.Serializable

@Serializable
data class Auctions private constructor(
	val auctions: List<Auction>
)
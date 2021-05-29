package payload.request

import kotlinx.serialization.Serializable
import payload.common.AuctionItem

@Serializable
data class AuctionCreate(
	val starting_price: Int,
	val buyout_price: Int?,
	val minimal_reputation: Int,
	val private: Boolean,
	val note: String,
	val item: AuctionItem
)
package payload.request

import payload.common.AuctionItem

data class AuctionCreate(
	val starting_price: Int,
	val buyout_price: Int?,
	val minimal_reputation: Int,
	val private: Boolean,
	val note: String,
	val item: AuctionItem
)
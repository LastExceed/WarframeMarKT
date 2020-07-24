package payload.request

data class AuctionUpdate(
	val starting_price: Int,
	val buyout_price: Int?,
	val minimal_reputation: Int,
	val private: Boolean,
	val note: String
)
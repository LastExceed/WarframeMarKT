package payload.response

import payload.response.common.Auction

data class AuctionsParticipant private constructor(
	val auctions: List<Auction>,
	val bids: List<Bids.Bid>
)
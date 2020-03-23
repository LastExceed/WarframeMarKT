package payload.response

import payload.response.common.Auction

data class Auctions private constructor(val auctions: List<Auction>)
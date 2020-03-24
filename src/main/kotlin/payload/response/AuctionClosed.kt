package payload.response

import enums.IdAuction

data class AuctionClosed private constructor(val auction_id: IdAuction)
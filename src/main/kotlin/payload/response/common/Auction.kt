package payload.response.common

import enums.*
import payload.common.AuctionItem
import kotlinx.datetime.*
import kotlinx.serialization.Serializable

@Serializable
data class Auction private constructor(
	val buyout_price: Int?,
	val visible: Boolean,
	val minimal_reputation: Int,
	val note: String,
	val item: AuctionItem,
	val private: Boolean,
	val starting_price: Int,
	val owner: UserShort,//TODO: userId when personal, UserShort when public
	val platform: Platform,
	val closed: Boolean,
	val top_bid: Int?,
	val winner: IdUser?,//maybe here too ?
	val is_marked_for: String?, //unknown
	val marked_operation_at: String?, //unknown
	val created: Instant,
	val updated: Instant,
	val note_raw: String,
	val is_direct_sell: Boolean,
	val id: IdAuction,
	val minimal_increment: Int? = null
)
package payload.response.common

import enums.*
import payload.common.AuctionItem
import kotlinx.datetime.*
import kotlinx.serialization.Serializable

sealed interface Auction : IdCarrier {
	override val id: IdAuction
	val buyout_price: Int?
	val visible: Boolean
	val minimal_reputation: Int
	val note: String
	val item: AuctionItem
	val private: Boolean
	val starting_price: Int
	val platform: Platform
	val closed: Boolean
	val top_bid: Int?
	val is_marked_for: String? //unknown
	val marked_operation_at: String? //unknown
	val created: Instant
	val updated: Instant
	val note_raw: String
	val is_direct_sell: Boolean
	val minimal_increment: Int?

	val owner: Any
	val winner: Any?
}

sealed interface AuctionOwnerId : Auction {
	override val owner: IdUser
}
sealed interface AuctionWinnerId : Auction {
	override val winner: IdUser?
}
sealed interface AuctionOwnerShort : Auction {
	override val owner: UserShort
}
sealed interface AuctionWinnerShort : Auction {
	override val winner: UserShort?
}

@Serializable
data class AuctionEntry private constructor(
	override val buyout_price: Int?,
	override val visible: Boolean,
	override val minimal_reputation: Int,
	override val note: String,
	override val item: AuctionItem,
	override val private: Boolean,
	override val starting_price: Int,
	override val platform: Platform,
	override val closed: Boolean,
	override val top_bid: Int?,
	override val is_marked_for: String?,
	override val marked_operation_at: String?,
	override val created: Instant,
	override val updated: Instant,
	override val note_raw: String,
	override val is_direct_sell: Boolean,
	override val id: IdAuction,
	override val minimal_increment: Int? = null,

	override val owner: IdUser,
	override val winner: IdUser?
) : AuctionOwnerId, AuctionWinnerId

@Serializable
data class AuctionEntryMixed private constructor(
	override val buyout_price: Int?,
	override val visible: Boolean,
	override val minimal_reputation: Int,
	override val note: String,
	override val item: AuctionItem,
	override val private: Boolean,
	override val starting_price: Int,
	override val platform: Platform,
	override val closed: Boolean,
	override val top_bid: Int?,
	override val is_marked_for: String?,
	override val marked_operation_at: String?,
	override val created: Instant,
	override val updated: Instant,
	override val note_raw: String,
	override val is_direct_sell: Boolean,
	override val id: IdAuction,
	override val minimal_increment: Int? = null,

	override val owner: UserShort,
	override val winner: IdUser?
) : AuctionOwnerShort, AuctionWinnerId

@Serializable
data class AuctionEntryExpanded private constructor(
	override val buyout_price: Int?,
	override val visible: Boolean,
	override val minimal_reputation: Int,
	override val note: String,
	override val item: AuctionItem,
	override val private: Boolean,
	override val starting_price: Int,
	override val platform: Platform,
	override val closed: Boolean,
	override val top_bid: Int?,
	override val is_marked_for: String?,
	override val marked_operation_at: String?,
	override val created: Instant,
	override val updated: Instant,
	override val note_raw: String,
	override val is_direct_sell: Boolean,
	override val id: IdAuction,
	override val minimal_increment: Int? = null,

	override val owner: UserShort,
	override val winner: UserShort?
) : AuctionOwnerShort, AuctionWinnerShort
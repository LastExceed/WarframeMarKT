package payload.response.common

import enums.*
import payload.common.AuctionItem
import kotlinx.datetime.*
import kotlinx.serialization.Serializable

@Serializable
sealed class Auction {
	abstract val buyout_price: Int?
	abstract val visible: Boolean
	abstract val minimal_reputation: Int
	abstract val note: String
	abstract val item: AuctionItem
	abstract val private: Boolean
	abstract val starting_price: Int
	abstract val platform: Platform
	abstract val closed: Boolean
	abstract val top_bid: Int?
	abstract val is_marked_for: String? //unknown
	abstract val marked_operation_at: String? //unknown
	abstract val created: Instant
	abstract val updated: Instant
	abstract val note_raw: String
	abstract val is_direct_sell: Boolean
	abstract val id: IdAuction
	abstract val minimal_increment: Int?

	abstract val owner: Any
	abstract val winner: Any?
}

//copypasty af - blame KycKyc

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
) : Auction()

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
) : Auction()

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
) : Auction()
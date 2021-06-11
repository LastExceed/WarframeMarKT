package enums

import kotlinx.serialization.Serializable
import payload.response.*
import payload.response.common.*

typealias IdAuction = Id<Auction>
typealias IdBid = Id<Bids.Bid>
typealias IdBan = Id<Bans.Ban>
typealias IdChat = Id<Chats.Chat>
typealias IdDucats = Id<Ducats>
typealias IdItem = Id<Item>
typealias IdMessage = Id<Chats.Chat.Message>
typealias IdOrder = Id<Order>
typealias IdReview = Id<Review>
typealias IdSnapshot = Id<ItemStatistics.Statistics.Snapshot>
typealias IdUser = Id<User>
typealias IdRivenItem = Id<RivenItems.RivenItem>
typealias IdRivenAttribute = Id<RivenAttributes.RivenAttribute>

typealias ItemUrlName = String
typealias ResourceLocation = String

@JvmInline
@Serializable
value class Id<T>(val value: String)
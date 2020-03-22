package payload.response

import payload.incoming.common.User

data class Auctions(val auctions: List<Auction>) {
	data class Auction(
		val buyout_price: Int,
		val minimal_reputation: Int,
		val note: String,
		val item: Item,
		val private: Boolean,
		val starting_price: Int,
		val owner: User,
		val platform: String,
		val closed: Boolean,
		val top_bid: Int,
		val winner: User,
		val is_marked_for: String?, //unknown
		val marked_operation_at: String?, //unknown
		val created: String,
		val updated: String,
		val note_raw: String,
		val is_direct_sale: Boolean,
		val id: String
	) {
		data class Item(
			val attributes: List<Attribute>,
			val polarity: String,
			val mod_rank: Int,
			val name: String,
			val re_rolls: Int,
			val mastery_level: Int,
			val weapon_url_name: String,
			val type: String
		) {
			data class Attribute(
				val value: Float,
				val positive: Boolean,
				val url_name: String
			)
		}
	}
}
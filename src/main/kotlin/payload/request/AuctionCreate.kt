package payload.request

data class AuctionCreate(
	val starting_price: Int,
	val buyout_price: Int?,
	val minimal_reputation: Int,
	val private: Boolean,
	val note: String,
	val item: Item
) {
	data class Item(
		val weapon_url_name: String,
		val name: String,//"Vexi-Cronicon"
		val type: String,//"riven"
		val attributes: List<Attribute>,
		val mastery_level: Int,
		val mod_rank: Int,
		val re_rolls: Int,
		val polarity: String
	) {
		data class Attribute(
			val url_name: String,//"slash_damage"
			val positive: Boolean,
			val value: Float
		)
	}
}
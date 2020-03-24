package payload.common

import enums.AuctionType
import enums.ItemUrlName
import enums.Polarity
import enums.RivenAttribute

data class ItemAuction(
	val weapon_url_name: ItemUrlName,
	val name: String,//rivens and liches have random generated names
	val type: AuctionType,
	val attributes: List<Attribute>,
	val mastery_level: Int,
	val mod_rank: Int,
	val re_rolls: Int,
	val polarity: Polarity
) {
	data class Attribute(
		val url_name: RivenAttribute,
		val positive: Boolean,
		val value: Float
	)
}
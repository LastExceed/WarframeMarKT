package payload.common

import enums.*
import kotlinx.serialization.*

@Serializable
sealed class AuctionItem {
	abstract val weapon_url_name: ItemUrlName
}

@Serializable
@SerialName("riven")
data class AuctionItemRiven(
	override val weapon_url_name: ItemUrlName,
	val name: String,//rivens and liches have random generated names
	val attributes: List<Attribute>,
	val mastery_level: Int,
	val mod_rank: Int,
	val re_rolls: Int,
	val polarity: Polarity
) : AuctionItem() {

	@Serializable
	data class Attribute(
		val url_name: RivenAttributeName,
		val positive: Boolean,
		val value: Float
	)
}

@Serializable
@SerialName("lich")
data class AuctionItemLich(
	override val weapon_url_name: ItemUrlName,
	val element: Element,
	val damage: Float,
	val having_ephemera: Boolean,
	val quirk: String? = null
) : AuctionItem()
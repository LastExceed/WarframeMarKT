package payload.common

import enums.*
import kotlinx.serialization.*

@Serializable
sealed class AuctionItem {
	abstract val weapon_url_name: ItemUrlName
}

@Serializable
sealed class AuctionItemKingpin : AuctionItem() {
	abstract val element: Element
	abstract val damage: Int
	abstract val having_ephemera: Boolean
	abstract val quirk: Quirk?
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
	override val element: Element,
	override val damage: Int,
	override val having_ephemera: Boolean,
	override val quirk: Quirk? = null
) : AuctionItemKingpin()

@Serializable
@SerialName("sister")
data class AuctionItemSister( // currently exactly the same as lich, but that can change any time
	override val weapon_url_name: ItemUrlName,
	override val element: Element,
	override val damage: Int,
	override val having_ephemera: Boolean,
	override val quirk: Quirk? = null
) : AuctionItemKingpin()
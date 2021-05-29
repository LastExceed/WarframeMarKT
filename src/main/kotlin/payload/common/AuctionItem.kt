package payload.common

import com.fasterxml.jackson.annotation.*
import enums.*
import kotlinx.serialization.Serializable

@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.PROPERTY,
	property = "type"
)
@JsonSubTypes(
	JsonSubTypes.Type(value = AuctionItemLich::class, name = "lich"),
	JsonSubTypes.Type(value = AuctionItemRiven::class, name = "riven")
)

@Serializable
abstract class AuctionItem {
	abstract val weapon_url_name: ItemUrlName
}

@Serializable
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
		val url_name: RivenAttribute,
		val positive: Boolean,
		val value: Float
	)
}

@Serializable
data class AuctionItemLich(
	override val weapon_url_name: ItemUrlName,
	val element: Element,
	val damage: Float,
	val having_ephemera: Boolean
) : AuctionItem()
package payload.response.common

import enums.*
import kotlinx.serialization.Serializable

@Serializable
sealed class Item {
	abstract val id: IdItem
}

@Serializable
sealed class ItemNamed : Item() {
	abstract val thumb: ResourceLocation
	abstract val url_name: ItemUrlName
}

@Serializable
data class ItemDescriptor private constructor(
	override val id: IdItem,
	val items_in_set: List<ItemFull>
) : Item()

@Serializable
data class ItemShort private constructor(
	override val id: IdItem,
	override val thumb: ResourceLocation,
	override val url_name: ItemUrlName,
	val item_name: String
) : ItemNamed()

@Serializable
data class ItemFull private constructor(
	override val id: IdItem,
	override val thumb: ResourceLocation,
	override val url_name: ItemUrlName,
	val mastery_level: Int? = null, //prime gear only
	val sub_icon: ResourceLocation?,
	val ducats: Int? = null, //prime parts only
	val tags: List<String>,
	val icon_format: String,//enum?
	val en: Lang,
	val ru: Lang,
	val de: Lang,
	val `zh-hant`: Lang,
	val `zh-hans`: Lang,
	val sv: Lang,
	val fr: Lang,
	val ko: Lang,
	val pt: Lang,
	val es: Lang,
	val pl: Lang,
	val trading_tax: Int? = null, //missing in OrderCreate
	val icon: ResourceLocation,
	val mod_max_rank: Int? = null,
	val rarity: Rarity? = null, //missing in OrderCreate
	val subtypes: List<String>? = null //for relic refinement - enum ?
) : ItemNamed() {
	@Serializable
	data class Lang private constructor(
		val wiki_link: String? = null,
		val item_name: String, //OrderCreate only has this
		val description: String? = null,
		val drop: List<Drop>? = null,
		val codex: String? = null, //in case of incomplete localization
		val thumb: ResourceLocation? = null, //in case of incomplete localization
		val icon: ResourceLocation? = null //in case of incomplete localization
	) {
		@Serializable
		data class Drop private constructor(
			val name: String,
			val link: String?
		)
	}
}
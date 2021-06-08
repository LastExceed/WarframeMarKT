package payload.response.common

import enums.*
import kotlinx.serialization.Serializable

@Serializable
data class ItemShort private constructor(
	val url_name: ItemUrlName,
	val id: IdItem,
	val item_name: String,
	val thumb: ResourceLocation
)

@Serializable
data class ItemDescriptor(
	val items_in_set: List<ItemFull>,
	val id: IdItem
)

@Serializable
data class ItemFull private constructor(
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
	val thumb: ResourceLocation,
	val id: IdItem,
	val icon: ResourceLocation,
	val url_name: ItemUrlName,
	val mod_max_rank: Int? = null,
	val rarity: Rarity? = null, //missing in OrderCreate
	val subtypes: List<String>? = null //for relic refinement - enum ?
) {
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
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
sealed class ItemData : ItemNamed() {
	abstract val ducats: Int? //prime stuff only
	abstract val icon: ResourceLocation
	abstract val icon_format: String//enum?
	abstract val mod_max_rank: Int? //mods only
	abstract val subtypes: List<String>? //eg relic refinement - enum ?
	abstract val sub_icon: ResourceLocation?
	abstract val tags: List<String>
	abstract val de: Lang
	abstract val en: Lang
	abstract val es: Lang
	abstract val fr: Lang
	abstract val ko: Lang
	abstract val pl: Lang
	abstract val pt: Lang
	abstract val ru: Lang
	abstract val sv: Lang
	abstract val `zh-hant`: Lang
	abstract val `zh-hans`: Lang

	@Serializable
	sealed class Lang protected constructor() {
		abstract val item_name: String
	}
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
open class ItemInOrder protected constructor(
	override val id: IdItem,
	override val thumb: ResourceLocation,
	override val url_name: ItemUrlName,
	override val ducats: Int? = null,
	override val icon: ResourceLocation,
	override val icon_format: String,
	override val mod_max_rank: Int? = null,
	override val subtypes: List<String>? = null,
	override val sub_icon: ResourceLocation?,
	override val tags: List<String>,
	override val de: Lang,
	override val en: Lang,
	override val es: Lang,
	override val fr: Lang,
	override val ko: Lang,
	override val pl: Lang,
	override val pt: Lang,
	override val ru: Lang,
	override val sv: Lang,
	override val `zh-hant`: Lang,
	override val `zh-hans`: Lang,
) : ItemData() {
	@Serializable
	data class Lang private constructor(
		override val item_name: String,
	) : ItemData.Lang()
}

@Serializable
data class ItemFull private constructor(
	override val id: IdItem,
	override val thumb: ResourceLocation,
	override val url_name: ItemUrlName,
	override val ducats: Int? = null, //prime stuff only
	override val icon: ResourceLocation,
	override val icon_format: String,//enum?
	override val mod_max_rank: Int? = null, //mods only
	override val subtypes: List<String>? = null, //for relic refinement - enum ?
	override val sub_icon: ResourceLocation?,
	override val tags: List<String>,
	override val de: Lang,
	override val en: Lang,
	override val es: Lang,
	override val fr: Lang,
	override val ko: Lang,
	override val pl: Lang,
	override val pt: Lang,
	override val ru: Lang,
	override val sv: Lang,
	override val `zh-hant`: Lang,
	override val `zh-hans`: Lang,

	val mastery_level: Int? = null,
	//set_root
	val rarity: Rarity? = null,
	val trading_tax: Int
) : ItemData() {
	@Serializable
	data class Lang private constructor(
		override val item_name: String,
		val wiki_link: String,
		val description: String,
		val drop: List<Drop>,
		val codex: String? = null, //in case of incomplete localization
		val thumb: ResourceLocation? = null, //in case of incomplete localization
		val icon: ResourceLocation? = null //in case of incomplete localization
	) : ItemData.Lang() {
		@Serializable
		data class Drop private constructor(
			val name: String,
			val link: String?
		)
	}
}
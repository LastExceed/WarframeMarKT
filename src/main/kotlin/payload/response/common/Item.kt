package payload.response.common

import enums.*
import kotlinx.serialization.Serializable

sealed interface Item : IdCarrier {
	val id: IdItem
}

sealed interface ItemNamed : Item {
	val thumb: ResourceLocation
	val url_name: ItemUrlName
}

sealed interface ItemData : ItemNamed {
	val ducats: Int? //prime stuff only
	val icon: ResourceLocation
	val icon_format: IconFormat
	val mod_max_rank: Int? //mods only
	val subtypes: List<String>? //eg relic refinement - enum ?
	val sub_icon: ResourceLocation?
	val tags: List<String>
	val de: Lang
	val en: Lang
	val es: Lang
	val fr: Lang
	val ko: Lang
	val pl: Lang
	val pt: Lang
	val ru: Lang
	val sv: Lang
	val `zh-hant`: Lang
	val `zh-hans`: Lang

	sealed interface Lang {
		val item_name: String
	}
}

@Serializable
data class ItemDescriptor private constructor(
	override val id: IdItem,
	val items_in_set: List<ItemFull>
) : Item

@Serializable
data class ItemShort private constructor(
	override val id: IdItem,
	override val thumb: ResourceLocation,
	override val url_name: ItemUrlName,
	val item_name: String
) : ItemNamed

@Serializable
data class ItemInOrder private constructor(
	override val id: IdItem,
	override val thumb: ResourceLocation,
	override val url_name: ItemUrlName,
	override val ducats: Int? = null,
	override val icon: ResourceLocation,
	override val icon_format: IconFormat,
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

	val quantity_for_set: Int? = null
) : ItemData {
	@Serializable
	data class Lang private constructor(
		override val item_name: String,
	) : ItemData.Lang
}

@Serializable
data class ItemFull private constructor(
	override val id: IdItem,
	override val thumb: ResourceLocation,
	override val url_name: ItemUrlName,
	override val ducats: Int? = null, //prime stuff only
	override val icon: ResourceLocation,
	override val icon_format: IconFormat,
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
	val set_root: Boolean? = null,
	val rarity: Rarity? = null,
	val trading_tax: Int
) : ItemData {
	@Serializable
	data class Lang private constructor(
		override val item_name: String,
		val wiki_link: String,
		val description: String,
		val drop: List<Drop>,
		val codex: String? = null, //in case of incomplete localization
		val thumb: ResourceLocation? = null, //in case of incomplete localization
		val icon: ResourceLocation? = null //in case of incomplete localization
	) : ItemData.Lang {
		@Serializable
		data class Drop private constructor(
			val name: String,
			val link: String?
		)
	}
}
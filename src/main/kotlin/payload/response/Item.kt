package payload.response

import enums.IdItem
import enums.ItemUrlName
import enums.Rarity
import enums.ResourceLocation

data class Item private constructor(val item: Item) {
	data class Item(
		var items_in_set: List<SetItem>,
		var id: IdItem
	) {
		data class SetItem private constructor(
			val set_root: Boolean,
			val mastery_level: Int?,
			val tradable: Boolean,
			val sub_icon: ResourceLocation?,
			val ducats: Int,
			val tags: List<String>,
			val icon_format: String,//enum?
			val en: Lang,
			val ru: Lang,
			val de: Lang,
			val zh: Lang,
			val sv: Lang,
			val fr: Lang,
			val ko: Lang,
			val trading_tax: Int,
			val thumb: ResourceLocation,
			val id: IdItem,
			val icon: ResourceLocation,
			val url_name: ItemUrlName,
			val mod_max_rank: Int?,
			val rarity: Rarity?
		) {
			data class Lang private constructor(
				val wiki_link: String,
				val item_name: String,
				val description: String,
				val drop: List<Drop>,
				val codex: String?,
				val thumb: ResourceLocation?,
				val icon: ResourceLocation?
			) {
				data class Drop private constructor(
					val name: String,
					val link: String?
				)
			}
		}
	}
}
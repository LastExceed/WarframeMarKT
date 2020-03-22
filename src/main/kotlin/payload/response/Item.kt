package payload.response

data class Item(val item: ItemInfo) {
	data class ItemInfo(
		var items_in_set: List<SetItem>,
		var id: String
	) {
		data class SetItem(
			val set_root: Boolean,
			val mastery_level: Int?,
			val tradable: Boolean,
			val sub_icon: String?,
			val ducats: Int,
			val tags: List<String>,
			val icon_format: String,
			val en: Lang,
			val ru: Lang,
			val de: Lang,
			val zh: Lang,
			val sv: Lang,
			val fr: Lang,
			val ko: Lang,
			val trading_tax: Int,
			val thumb: String,
			val id: String,
			val icon: String,
			val url_name: String,
			val mod_max_rank: Int?,
			val rarity: String?
		) {
			data class Lang(
				val wiki_link: String,
				val item_name: String,
				val description: String,
				val drop: List<Drop>,
				val codex: String?,
				val thumb: String?,
				val icon: String?
			) {
				data class Drop(
					val name: String,
					val link: String?
				)
			}
		}
	}
}
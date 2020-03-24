package payload.response

import enums.IdItem
import enums.ItemUrlName
import enums.ResourceLocation


data class Items private constructor(val items: List<Item>) {
	data class Item private constructor(
		val url_name: ItemUrlName,
		val id: IdItem,
		val item_name: String,
		val thumb: ResourceLocation
	)
}
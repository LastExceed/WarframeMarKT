package payload.response

import enums.IdItem
import enums.ItemUrlName
import enums.ResourceLocation
import kotlinx.serialization.Serializable

@Serializable
data class Items private constructor(val items: List<ItemShort>) {
	@Serializable
	data class ItemShort private constructor(
		val url_name: ItemUrlName,
		val id: IdItem,
		val item_name: String,
		val thumb: ResourceLocation
	)
}
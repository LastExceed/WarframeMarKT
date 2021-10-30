package payload.response

import enums.*
import kotlinx.serialization.Serializable

@Serializable
data class RivenItems private constructor(
	val items: List<RivenItem>
) {
	@Serializable
	data class RivenItem(
		override val id: Id<RivenItem>,
		val thumb: ResourceLocation,
		val url_name: ItemUrlName,
		val item_name: String,
		val riven_type: RivenType,
		val icon: ResourceLocation,
		val group: WeaponGroup,
		val icon_format: IconFormat
	) : IdCarrier
}

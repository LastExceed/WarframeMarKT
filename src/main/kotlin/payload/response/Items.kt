package payload.response

import kotlinx.serialization.Serializable
import payload.response.common.ItemShort

@Serializable
data class Items private constructor(
	val items: List<ItemShort>
)
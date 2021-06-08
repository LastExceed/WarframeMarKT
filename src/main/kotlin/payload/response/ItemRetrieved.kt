package payload.response

import kotlinx.serialization.Serializable
import payload.response.common.Item

@Serializable
data class ItemRetrieved private constructor(
	val item: Item
)
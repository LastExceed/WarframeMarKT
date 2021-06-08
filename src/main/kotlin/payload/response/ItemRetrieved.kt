package payload.response

import kotlinx.serialization.Serializable
import payload.response.common.ItemDescriptor

@Serializable
data class ItemRetrieved private constructor(
	val item: ItemDescriptor
)
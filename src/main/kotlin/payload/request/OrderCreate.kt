package payload.request

import enums.*
import kotlinx.serialization.Serializable
import payload.response.common.Item

@Serializable
data class OrderCreate(
	val order_type: OrderType,
	val item_id: Id<Item>,
	val platinum: Int,
	val quantity: Int,
	val rank: Int? = null
)
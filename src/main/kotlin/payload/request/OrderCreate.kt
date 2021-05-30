package payload.request

import enums.*
import kotlinx.serialization.Serializable

@Serializable
data class OrderCreate(
	val order_type: OrderType,
	val item_id: IdItem,
	val platinum: Int,
	val quantity: Int,
	val rank: Int? = null
)
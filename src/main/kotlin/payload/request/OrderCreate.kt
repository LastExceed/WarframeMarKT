package payload.request

import enums.IdItem
import enums.OrderType

data class OrderCreate(
	val order_type: OrderType,
	val item_id: IdItem,
	val platinum: Int,
	val quantity: Int,
	val mod_rank: Int? = null
)
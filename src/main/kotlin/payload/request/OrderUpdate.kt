package payload.request

import enums.IdItem
import enums.IdOrder

data class OrderUpdate(
	val order_id: IdOrder,
	val visible: Boolean,
	val item_id: IdItem,
	val platinum: Int,
	val quantity: Int,
	val mod_rank: Int? = null
)
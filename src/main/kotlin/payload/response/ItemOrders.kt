package payload.response

import kotlinx.serialization.Serializable
import payload.response.common.OrderFromItem

@Serializable
data class ItemOrders private constructor(
	val orders: List<OrderFromItem>
)
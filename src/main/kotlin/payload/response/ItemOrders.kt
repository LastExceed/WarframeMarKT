package payload.response

import kotlinx.serialization.Serializable
import payload.response.common.Order

@Serializable
data class ItemOrders private constructor(
	val orders: List<Order>
)
package payload.response

import payload.response.common.Order
import kotlinx.serialization.Serializable

@Serializable
data class UserOrders private constructor(
	val buy_orders: List<Order>,
	val sell_orders: List<Order>
)
package payload.response

import payload.response.common.Order
import kotlinx.serialization.Serializable

@Serializable
data class ItemOrdersTop private constructor(
	val sell_orders: List<Order>,
	val buy_orders: List<Order>
)
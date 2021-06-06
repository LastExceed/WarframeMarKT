package payload.response

import payload.response.common.OrderFromItem
import kotlinx.serialization.Serializable

@Serializable
data class ItemOrdersTop private constructor(
	val sell_orders: List<OrderFromItem>,
	val buy_orders: List<OrderFromItem>
)
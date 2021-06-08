package payload.response

import payload.response.common.OrderFromRecent
import kotlinx.serialization.Serializable

@Serializable
data class RecentOrders private constructor(
	val buy_orders: List<OrderFromRecent>,
	val sell_orders: List<OrderFromRecent>
)
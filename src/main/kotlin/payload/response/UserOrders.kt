package payload.response

import payload.response.common.OrderFromProfile
import kotlinx.serialization.Serializable

@Serializable
data class UserOrders private constructor(
	val buy_orders: List<OrderFromProfile>,
	val sell_orders: List<OrderFromProfile>
)
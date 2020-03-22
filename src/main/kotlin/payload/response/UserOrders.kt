package payload.response

import payload.response.common.Order

data class UserOrders(
	val buy_orders: List<Order>,
	val sell_orders: List<Order>
)
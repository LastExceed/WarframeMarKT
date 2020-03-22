package payload.response

import payload.response.common.Order

data class ItemOrdersTop(
	val sell_orders: List<Order>,
	val buy_orders: List<Order>
)
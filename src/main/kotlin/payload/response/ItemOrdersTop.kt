package payload.response

import payload.incoming.common.Order

data class ItemOrdersTop(
	val sell_orders: List<Order>,
	val buy_orders: List<Order>
)
package payload.response

import payload.incoming.common.Order

data class ItemOrders(val orders: List<Order>)
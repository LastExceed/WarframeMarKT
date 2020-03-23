package payload.response

import payload.response.common.Order

data class ItemOrders private constructor(val orders: List<Order>)
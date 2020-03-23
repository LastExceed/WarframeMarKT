package payload.response

import payload.response.common.Order

data class OwnOrders private constructor(val orders: List<Order>)
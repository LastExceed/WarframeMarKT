package payload.response

import payload.response.common.Order
import kotlinx.serialization.Serializable

@Serializable
data class OwnOrders private constructor(val orders: List<Order>)
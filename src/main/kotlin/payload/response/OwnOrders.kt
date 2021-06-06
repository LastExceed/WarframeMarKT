package payload.response

import payload.response.common.OrderFromProfile
import kotlinx.serialization.Serializable

@Serializable
data class OwnOrders private constructor(
	val orders: List<OrderFromProfile>
)
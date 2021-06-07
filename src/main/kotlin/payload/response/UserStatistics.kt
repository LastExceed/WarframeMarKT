package payload.response

import kotlinx.serialization.Serializable
import payload.response.common.OrderFromProfileStatistics

@Serializable
data class UserStatistics private constructor(
	val closed_orders: List<OrderFromProfileStatistics>
)
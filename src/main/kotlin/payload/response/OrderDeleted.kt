package payload.response

import enums.Id
import kotlinx.serialization.Serializable
import payload.response.common.Order

@Serializable
data class OrderDeleted private constructor(
	val order_id: Id<Order>
)
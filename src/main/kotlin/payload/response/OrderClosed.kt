package payload.response

import payload.response.common.Order
import kotlinx.serialization.Serializable

@Serializable
data class OrderClosed private constructor(
	val order: Order?
)
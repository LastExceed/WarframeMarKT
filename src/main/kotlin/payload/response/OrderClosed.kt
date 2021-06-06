package payload.response

import kotlinx.serialization.Serializable
import payload.response.common.OrderFromClosure

@Serializable
data class OrderClosed private constructor(
	val order: OrderFromClosure?
)
package payload.response

import kotlinx.serialization.Serializable
import payload.response.common.OrderFromProfile

@Serializable
data class OrderUpdated private constructor(
	val order: OrderFromProfile
)
package payload.response

import kotlinx.serialization.Serializable
import payload.response.common.*

@Serializable
data class OrderCreated private constructor(
	val order: OrderFromProfile
)
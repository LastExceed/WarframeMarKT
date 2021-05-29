package payload.response

import enums.IdOrder
import kotlinx.serialization.Serializable

@Serializable
data class OrderDeleted private constructor(val order_id: IdOrder)
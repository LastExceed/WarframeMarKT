package payload.request

import enums.IdOrder
import kotlinx.serialization.Serializable

@Serializable
data class OrderUpdate(
	val order_id: IdOrder,
	val visible: Boolean,
	val platinum: Int,
	val quantity: Int,
	val mod_rank: Int? = null
)
package payload.request

import enums.Id
import kotlinx.serialization.Serializable
import payload.response.common.Order

@Serializable
data class OrderUpdate(
	val order_id: Id<Order>, //we already need to specify it in the url, but also have to put it here again. KycKyc pls
	val visible: Boolean,
	val platinum: Int,
	val quantity: Int,
	val rank: Int? = null
)
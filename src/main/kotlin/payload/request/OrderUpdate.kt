package payload.request

import enums.IdOrder
import kotlinx.serialization.Serializable

@Serializable
data class OrderUpdate(//TODO: inherit from OrderCreate or sth
	val order_id: IdOrder, //we already need to specify it in the url, but also have to put it here again. KycKyc pls
	val visible: Boolean,
	val platinum: Int,
	val quantity: Int,
	val rank: Int? = null
)
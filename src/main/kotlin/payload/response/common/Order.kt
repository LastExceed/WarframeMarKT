package payload.response.common

import enums.IdOrder
import enums.OrderType
import enums.Platform
import enums.Region
import java.util.*

data class Order private constructor(
	val creation_date: Date? = null, //missing in order update response
	val id: IdOrder,
	val last_update: Date,
	val order_type: OrderType,
	val platform: Platform,
	val platinum: Int,
	val quantity: Int,
	val region: Region,
	val visible: Boolean,
	val mod_rank: Int? = null, //missing if not a mod
	val item: Any? = null, //missing if specified in request
	val user: User? = null, //missing if specified in request
	val closed_date: String? = null //missing if not personal order
) {
	data class ItemId(val value: String)
}
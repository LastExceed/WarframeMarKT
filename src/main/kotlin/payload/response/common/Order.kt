package payload.response.common

import enums.IdOrder
import enums.OrderType
import enums.Platform
import enums.Region
import kotlinx.datetime.*
import kotlinx.serialization.*

@Serializable
data class Order private constructor(
	val creation_date: Instant,
	val id: IdOrder,
	val last_update: Instant,
	val order_type: OrderType,
	val platform: Platform,
	val platinum: Double, //sometimes there's .0 at the end. wtf KycKyc
	val quantity: Int,
	val region: Region,
	val visible: Boolean,
	val mod_rank: Int? = null, //missing if not a mod
	val item: Item.Item.SetItem? = null, //missing if specified in request
	val user: UserShort? = null, //missing if specified in request
	val closed_date: Instant? = null //missing if not closed
)
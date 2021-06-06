package payload.response.common

import enums.*
import kotlinx.datetime.*
import kotlinx.serialization.*
import payload.response.*

@Serializable
sealed class Order {
	abstract val creation_date: Instant
	abstract val id: IdOrder
	abstract val last_update: Instant
	abstract val order_type: OrderType
	abstract val platform: Platform
	abstract val platinum: Float //sometimes there's .0 at the end. wtf KycKyc
	abstract val quantity: Int
	abstract val region: Region
	abstract val visible: Boolean
	abstract val mod_rank: Int? //missing if not a mod
}

@Serializable
data class OrderFromItem private constructor(
	override val creation_date: Instant,
	override val id: IdOrder,
	override val last_update: Instant,
	override val order_type: OrderType,
	override val platform: Platform,
	override val platinum: Float,
	override val quantity: Int,
	override val region: Region,
	override val visible: Boolean,
	override val mod_rank: Int? = null, //missing if not a mod

	val user: UserShort? = null //missing if specified in request
) : Order()

@Serializable
data class OrderFromProfile private constructor(
	override val creation_date: Instant,
	override val id: IdOrder,
	override val last_update: Instant,
	override val order_type: OrderType,
	override val platform: Platform,
	override val platinum: Float,
	override val quantity: Int,
	override val region: Region,
	override val visible: Boolean,
	override val mod_rank: Int? = null,

	val item: Item.Item.SetItem,
) : Order()

@Serializable
data class OrderFromProfileStatistics private constructor(
	override val creation_date: Instant,
	override val id: IdOrder,
	override val last_update: Instant,
	override val order_type: OrderType,
	override val platform: Platform,
	override val platinum: Float,
	override val quantity: Int,
	override val region: Region,
	override val visible: Boolean,
	override val mod_rank: Int? = null,

	val item: Item.Item.SetItem,
	val closed_date: Instant
) : Order()

@Serializable
data class OrderFromClosure private constructor(
	override val creation_date: Instant,
	override val id: IdOrder,
	override val last_update: Instant,
	override val order_type: OrderType,
	override val platform: Platform,
	override val platinum: Float,
	override val quantity: Int,
	override val region: Region,
	override val visible: Boolean,
	override val mod_rank: Int? = null,

	val item: IdItem
) : Order()
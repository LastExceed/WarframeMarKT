package payload.response.common

import enums.*
import kotlinx.datetime.*
import kotlinx.serialization.*

sealed interface OrderShort {
	val id: IdOrder
	val order_type: OrderType
	val platform: Platform
	val platinum: Float //sometimes there's .0 at the end. wtf KycKyc
	val quantity: Int
	val region: Region
	val mod_rank: Int? //missing if not a mod
	val subtype: String? //eg relic refinement, missing if not applicable
}

sealed interface Order : OrderShort {
	val creation_date: Instant
	val last_update: Instant
	val visible: Boolean
}

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
	override val mod_rank: Int? = null,
	override val subtype: String? = null,

	val user: UserShort? = null
) : Order

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
	override val subtype: String? = null,

	val item: ItemInOrder,
) : Order

@Serializable
data class OrderFromRecent private constructor(
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
	override val subtype: String? = null,

	val item: ItemInOrder,
	val user: UserShort? = null
) : Order

@Serializable
data class OrderFromProfileStatistics private constructor(
	override val id: IdOrder,
	override val order_type: OrderType,
	override val platform: Platform,
	override val platinum: Float,
	override val quantity: Int,
	override val region: Region,
	override val mod_rank: Int? = null,
	override val subtype: String? = null,

	val item: ItemInOrder,
	val closed_date: Instant
) : OrderShort

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
	override val subtype: String? = null,

	val item: IdItem
) : Order
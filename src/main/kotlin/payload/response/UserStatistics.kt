package payload.response

import enums.*
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class UserStatistics private constructor(val closed_orders: List<ClosedOrder>) {

	@Serializable
	data class ClosedOrder private constructor(
		val item: Map<String, String>,
		val closed_date: Instant,
		val region: Region,
		val order_type: OrderType,
		val platinum: Int,
		val id: Int,
		val quantity: Int,
		val mod_rank: Int?,
		val platform: Platform
	)
}
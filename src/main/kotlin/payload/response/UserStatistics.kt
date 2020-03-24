package payload.response

import enums.OrderType
import enums.Platform
import enums.Region
import java.util.*

data class UserStatistics private constructor(val closed_orders: List<ClosedOrder>) {
	data class ClosedOrder private constructor(
		val item: Map<String, String>,
		val closed_date: Date,
		val region: Region,
		val order_type: OrderType,
		val platinum: Int,
		val id: Int,
		val quantity: Int,
		val mod_rank: Int?,
		val platform: Platform
	)
}
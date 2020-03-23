package payload.response

data class UserStatistics private constructor(val closed_orders: List<ClosedOrder>) {
	data class ClosedOrder private constructor(
		val item: Map<String, String>,
		val closed_date: String,
		val region: String,
		val order_type: String,
		val platinum: Int,
		val id: Int,
		val quantity: Int,
		val mod_rank: Int?,
		val platform: String
	)
}
package payload.response

data class UserStatistics(val closed_orders: List<ClosedOrder>) {
	data class ClosedOrder(
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
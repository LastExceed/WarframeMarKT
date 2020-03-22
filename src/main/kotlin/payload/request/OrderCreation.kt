package payload.request

data class OrderCreation(
	val order_type: String,
	val item_id: String,
	val platinum: Int,
	val quantity: Int,
	val mod_rank: Int? = null
)
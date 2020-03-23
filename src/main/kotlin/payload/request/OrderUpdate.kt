package payload.request

data class OrderUpdate(
	val order_id: String,
	val visible: Boolean,
	val item_id: String,
	val platinum: Int,
	val quantity: Int,
	val mod_rank: Int? = null
)
package payload.response.common

data class Order private constructor(
	val creation_date: String,
	val id: String,
	val last_update: String,
	val order_type: String,
	val platform: String,
	val platinum: Int,
	val quantity: Int,
	val region: String,
	val visible: Boolean,
	val mod_rank: Int? = null, //null if not a mod
	val item: Any? = null, //null if specified in request
	val user: User? = null, //null if specified in request
	val closed_date: String? = null //null if not personal order
)
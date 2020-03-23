package payload.response

data class Items private constructor(val items: List<Item>) {
	data class Item private constructor(
		val url_name: String,
		val id: String,
		val item_name: String,
		val thumb: String
	)
}

package payload.response

data class Items(val items: List<Item>) {
	data class Item(
		val url_name: String,
		val id: String,
		val item_name: String,
		val thumb: String
	)
}

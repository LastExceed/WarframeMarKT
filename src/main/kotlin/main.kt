import payload.response.Item

suspend fun main() {
	val x = WarframeMarket.v1.items.ITEM("maiming_strike").get()
	println(x.item.id)
}
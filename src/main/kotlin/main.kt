suspend fun main() {
	try {
		val x = WarframeMarket.items.URL_NAME("maiming_strike").get()
		println("success")
		println(x)
	} catch (ex: Exception) {
		println("error")
		println(ex.message)
	}

}

//TODO:
//- accessibility modifiers
//  - private constructors
//- rename variables using annotations
//- handle null values in payloads explicitly
//- rename confirmation payloads

//POST customization omitted because undocumented

//dont use type hierachy for return types because of recurring return types
//GET single order doesnt exist anymore
//GET settings moved to settings/verification
// [close order] is  profile/orders/close/{ID}  instead of  profile/orders/{ID}/close
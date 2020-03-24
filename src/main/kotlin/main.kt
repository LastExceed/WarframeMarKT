import payload.request.SigninCredentials

typealias mx = Int
typealias my = Int

suspend fun main() {
	try {
		val profile = WarframeMarket.v1.auth.signIn(
			SigninCredentials(
				"chris20194@googlemail.com",
				"pIemCfgZ5ym7^6!Xmqhf5@kNuGR@tkvr77GA9\$fE"
			)
		)
		println("ok")

		val x = WarframeMarket.v1.items.get()
		println(x.items.size)
		val y = WarframeMarket.v1.profile.USER("LastExceed").get()
		println(y.profile.last_seen)
	} catch (ex: Exception) {
		println(ex.message)
	}
	println("done")
	readLine()
}

//TODO:
//implement include
//check if kotlin 1.4 fixes type erasure
//getting reviews without auth breaks because for some reason it includes the personal profile which is full of nulls when not logged in
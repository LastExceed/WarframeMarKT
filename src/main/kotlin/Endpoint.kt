abstract class Endpoint internal constructor(val parent: Endpoint?) : Requestable {
	open val pathName
		get() = this::class.simpleName!!.toLowerCase()

	override val url: String
		get() = parent!!.url + '/' + pathName
}
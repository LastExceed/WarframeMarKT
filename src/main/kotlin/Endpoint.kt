abstract class Endpoint internal constructor(internal val parent: Endpoint?) : Requestable {
	internal open val pathName
		get() = this::class.simpleName!!.toLowerCase()

	override val url: String
		get() = parent!!.url + '/' + pathName
}
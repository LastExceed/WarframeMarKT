abstract class Endpoint internal constructor(private val parent: Endpoint?) : Requestable {
	protected open val pathName
		get() = this::class.simpleName!!

	override val url: String
		get() = parent!!.url + '/' + pathName
}
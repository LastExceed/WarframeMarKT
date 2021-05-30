import io.ktor.client.*
import io.ktor.client.features.cookies.*
import io.ktor.client.request.*
import io.ktor.http.*
import payload.PayloadContainer

abstract class Endpoint internal constructor(private val parent: Endpoint?) : Requestable {
	override val httpClient: HttpClient
		get() = parent!!.httpClient
	protected open val pathName
		get() = this::class.simpleName!!.lowercase()
	override val url: String
		get() = parent!!.url + '/' + pathName

	protected suspend inline fun <reified ResponseType> requestUnwrapped(
		method: HttpMethod,
		payload: Any? = null,
		url: String = this.url,
		block: HttpRequestBuilder.() -> Unit = {}
	) = httpClient.request<PayloadContainer<ResponseType>>(url) {
		this.method = method
		contentType(ContentType.Application.Json)
		accept(ContentType.Application.Json)
		header("language", WarframeMarket.language)
		header("platform", WarframeMarket.platform)
		val token = httpClient.cookies(WarframeMarket.v1.auth.url + "/signin").find { it.name == "JWT" }?.value ?: ""
		header("Authorization", "JWT $token")
		if (payload != null) {
			body = payload
		}
		block()
	}.payload

//	protected suspend inline fun <reified ResponseType> requestUnwrappedWorkaround(
//		method: HttpMethod,
//		payload: Any? = null,
//		url: String = this.url,
//		block: HttpRequestBuilder.() -> Unit = {}
//	): ResponseType {
//		val jsonString = httpClient.request<String>(url) {
//			this.method = method
//			contentType(ContentType.Application.Json)
//			accept(ContentType.Application.Json)
//			header("language", WarframeMarket.language)
//			header("platform", WarframeMarket.platform)
//			val token = httpClient.cookies(WarframeMarket.v1.auth.url + "/signin").find { it.name == "JWT" }?.value ?: ""
//			header("Authorization", "JWT $token")
//			if (payload != null) {
//				body = payload
//			}
//			block()
//		}
//
//		//workaround for https://youtrack.jetbrains.com/issue/KT-22798
//
//		val valueType = object : ParameterizedType {
//			override fun getActualTypeArguments(): Array<Type> = arrayOf(ResponseType::class.java)
//			override fun getRawType(): Type = PayloadContainer::class.java
//			override fun getOwnerType(): Type? = null
//		}
//		val mapper = ObjectMapper().registerKotlinModule()
//		return mapper.readValue<PayloadContainer<ResponseType>>(jsonString, mapper.constructType(valueType)).payload
//	}
}
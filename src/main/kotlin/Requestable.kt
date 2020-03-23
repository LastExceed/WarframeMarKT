import io.ktor.client.HttpClient
import io.ktor.client.features.cookies.cookies
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import payload.PayloadContainer

interface Requestable {
	val url: String
}

interface Get<ResponseType> : Requestable {
	suspend fun get(): ResponseType// = httpClient.requestUnwrapped<ResponseType>(url, HttpMethod.Get)
}

interface Create<RequestType, ResponseType> : Requestable {
	suspend fun create(payload: RequestType): ResponseType// = httpClient.requestUnwrapped<ResponseType>(url, HttpMethod.Post)
}

interface Update<RequestType, ResponseType> : Requestable {
	suspend fun update(payload: RequestType): ResponseType// = httpClient.requestUnwrapped<ResponseType>(url, HttpMethod.Put)
}

interface Delete<ResponseType> : Requestable {
	suspend fun delete(): ResponseType// = httpClient.requestUnwrapped<ResponseType>(url, HttpMethod.Delete)
}

private suspend inline fun <reified ResponseType> HttpClient.requestUnwrapped(
	url: String,
	method: HttpMethod,
	payload: Any? = null
) = request<PayloadContainer<ResponseType>>(url) {
	this.method = method
	contentType(ContentType.Application.Json)
	accept(ContentType.Application.Json)
	header("language", WarframeMarket.language)
	header("platform", WarframeMarket.platform)
	val token = cookies(WarframeMarket.v1.auth.url + "/signin").find { it.name == "JWT" }!!.value
	header("Authorization", "JWT $token")
	if (payload != null) {
		body = payload
	}
}.payload
import io.ktor.client.HttpClient

internal interface Requestable {
	val httpClient: HttpClient
	val url: String
}

//interface type parameters aren't reified so the implementation needs to be inlined everywhere FeelsBadMan
internal interface Get<ResponseType> : Requestable {
	suspend fun get(): ResponseType// = requestUnwrapped<ResponseType>(HttpMethod.Get)
}

internal interface Create<RequestType, ResponseType> : Requestable {
	suspend fun create(payload: RequestType): ResponseType// = requestUnwrapped<ResponseType>(HttpMethod.Post)
}

internal interface Update<RequestType, ResponseType> : Requestable {
	suspend fun update(payload: RequestType): ResponseType// = requestUnwrapped<ResponseType>(HttpMethod.Put)
}

internal interface Delete<ResponseType> : Requestable {
	suspend fun delete(): ResponseType// = requestUnwrapped<ResponseType>(HttpMethod.Delete)
}
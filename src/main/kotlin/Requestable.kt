import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.module.kotlin.*
import io.ktor.client.features.cookies.cookies
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import payload.*
import java.lang.reflect.*


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
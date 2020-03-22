

import io.ktor.client.HttpClient
import io.ktor.client.features.cookies.AcceptAllCookiesStorage
import io.ktor.client.features.cookies.HttpCookies
import io.ktor.client.features.cookies.cookies
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import payload.request.*
import payload.response.*
import payload.response.common.Order
import java.net.URL

//naming conventions are intentionally violated to represent the api (it is case sensitive)
//also helps with disambiguation between endpoints and payload types
@Suppress("ClassName", "unused", "FunctionName")
object WarframeMarket : Endpoint(null) {
	override val url = "https://api.warframe.market/v1"
	var language = "en"
	var platform = "pc"

	private val httpClient = HttpClient {
		install(JsonFeature) {
			serializer = JacksonSerializer()
		}
		install(HttpCookies) {
			storage = AcceptAllCookiesStorage()
		}
	}

	private var jwt = ""

	private fun createRequestBuilder(url: String, payload: Any? = null) =
		HttpRequestBuilder(URL(url)).apply {
			contentType(ContentType.Application.Json)
			accept(ContentType.Application.Json)
			header("language", language)
			header("platform", platform)
			header("Authorization", "JWT $jwt")
			if (payload != null) {
				body = payload
			}
		}

	object v1 : Endpoint(WarframeMarket) {
		object auctions : Endpoint(WarframeMarket), Get<Auctions> {
			override suspend fun get() = httpClient.get<PayloadContainer<Auctions>>(url).payload
		}

		object auth : Endpoint(WarframeMarket) {
			suspend fun signIn(payload: Credentials) {
				httpClient.post<PayloadContainer<ProfilePrivate>>(createRequestBuilder("$url/signin", payload)).payload
				jwt = httpClient.cookies("$url/signin").find { it.name == "JWT" }!!.value
			}

			suspend fun signOut(): Unit { //returns a profile with all values null
				httpClient.get<HttpResponse>("$url/signout")
				jwt = ""
			}

			suspend fun register(): Nothing = TODO()
		}

		object items : Endpoint(WarframeMarket), Get<Items> {
			override suspend fun get() = httpClient.get<PayloadContainer<Items>>(createRequestBuilder(url)).payload

			class ITEM(url_name: String) : Endpoint(items), Get<Item> {
				override val pathName = url_name

				override suspend fun get() = httpClient.get<PayloadContainer<Item>>(createRequestBuilder(url)).payload

				val orders = Orders(this)
				val statistics = Statistics(this)

				class Orders(parent: Endpoint?) : Endpoint(parent), Get<ItemOrders> {
					override suspend fun get() = httpClient.get<PayloadContainer<ItemOrders>>(createRequestBuilder(url)).payload

					val top = Top(this)

					class Top(parent: Endpoint?) : Endpoint(parent), Get<ItemOrdersTop> {
						override suspend fun get() = httpClient.get<PayloadContainer<ItemOrdersTop>>(createRequestBuilder(url)).payload
					}
				}

				class Statistics(parent: Endpoint?) : Endpoint(parent), Get<ItemStatistics> {
					override suspend fun get() = httpClient.get<PayloadContainer<ItemStatistics>>(createRequestBuilder(url)).payload
				}
			}
		}

		object tools : Endpoint(WarframeMarket) {
			object ducats : Endpoint(tools), Get<Ducanator> {
				override suspend fun get() = httpClient.get<PayloadContainer<Ducanator>>(createRequestBuilder(url)).payload
			}
		}

		object profile : Endpoint(WarframeMarket), Get<ProfilePrivate> {
			override suspend fun get() = httpClient.get<PayloadContainer<ProfilePrivate>>(createRequestBuilder(url)).payload

			object customization : Endpoint(profile) {
				object about : Endpoint(customization), Update<AboutUpdate, About> {
					override suspend fun update(payload: AboutUpdate) = httpClient.put<PayloadContainer<About>>(createRequestBuilder(url, payload)).payload
				}

				object avatar : Endpoint(customization), Create<Nothing, ProfilePrivate> {
					override suspend fun create(payload: Nothing) = TODO("not implemented")
				}
			}

			object orders : Endpoint(profile), Get<OwnOrders>, Create<OrderCreation, Order> {
				override suspend fun get() = httpClient.get<PayloadContainer<OwnOrders>>(createRequestBuilder(url)).payload

				override suspend fun create(payload: OrderCreation) = httpClient.post<PayloadContainer<Order>>(createRequestBuilder(url, payload)).payload

				class ORDER(order_id: String) : Endpoint(orders), Update<OrderUpdate, Order>, Delete<OrderDeletionConfirmation> {
					override val pathName = order_id

					override suspend fun update(payload: OrderUpdate) = httpClient.put<PayloadContainer<Order>>(createRequestBuilder(url, payload)).payload

					override suspend fun delete() = httpClient.delete<PayloadContainer<OrderDeletionConfirmation>>(createRequestBuilder(url)).payload

					suspend fun close() = httpClient.put<PayloadContainer<OrderCloseConfirmation>>(createRequestBuilder(orders.url + "/close/" + pathName)).payload
				}
			}

			class USER(username: String) : Endpoint(profile), Get<ProfilePublic> {
				override val pathName = username

				override suspend fun get() = httpClient.get<PayloadContainer<ProfilePublic>>(createRequestBuilder(url)).payload

				val achievements = Achievements(this)
				val orders = Orders(this)
				val review = Review(this)
				val reviews = Reviews(this)
				val statistics = Statistics(this)

				class Achievements(parent: Endpoint?) : Endpoint(parent), Get<UserAchievements> {
					override suspend fun get() = httpClient.get<PayloadContainer<UserAchievements>>(createRequestBuilder(url)).payload
				}

				class Orders(parent: Endpoint?) : Endpoint(parent), Get<UserOrders> {
					override suspend fun get() = httpClient.get<PayloadContainer<UserOrders>>(createRequestBuilder(url)).payload
				}

				class Review(parent: Endpoint?) : Endpoint(parent), Create<ReviewCreation, ReviewCreationConfirmation> {
					override suspend fun create(payload: ReviewCreation) = httpClient.post<PayloadContainer<ReviewCreationConfirmation>>(createRequestBuilder(url, payload)).payload

					fun REVIEW(review_id: String) = REVIEW(review_id, this)

					class REVIEW(override val pathName: String, parent: Endpoint?) : Endpoint(parent), Update<ReviewUpdate, ReviewUpdateConfirmation>, Delete<ReviewDeletionConfirmation> {
						override suspend fun update(payload: ReviewUpdate) = httpClient.put<PayloadContainer<ReviewUpdateConfirmation>>(createRequestBuilder(url, payload)).payload

						override suspend fun delete() = httpClient.delete<PayloadContainer<ReviewDeletionConfirmation>>(createRequestBuilder(url)).payload
					}
				}

				class Reviews(parent: Endpoint?) : Endpoint(parent), Get<Reviews> {
					override suspend fun get() = httpClient.get<PayloadContainer<Reviews>>(createRequestBuilder(url)).payload
				}

				class Statistics(parent: Endpoint?) : Endpoint(parent), Get<UserStatistics> {
					override suspend fun get() = httpClient.get<PayloadContainer<UserStatistics>>(createRequestBuilder(url)).payload
				}
			}
		}

		object settings : Endpoint(WarframeMarket) {
			object notifications : Endpoint(settings) {
				object push : Endpoint(settings), Create<PushNotification, String>, Delete<String> {
					override suspend fun create(payload: PushNotification) = httpClient.post<PayloadContainer<String>>(createRequestBuilder(url)).payload

					override suspend fun delete() = httpClient.delete<PayloadContainer<String>>(createRequestBuilder(url)).payload
				}
			}

			object verification : Endpoint(settings) {
				suspend fun patch(payload: VerificationPatch) = httpClient.patch<PayloadContainer<VerificationPatchConfirmation>>(createRequestBuilder(url, payload)).payload
			}
		}
	}
}
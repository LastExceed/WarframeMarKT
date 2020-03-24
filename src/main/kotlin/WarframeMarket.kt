import enums.Platform
import enums.Region
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
import payload.PayloadContainer
import payload.request.*
import payload.response.*
import payload.response.common.Order
import payload.response.common.User
import java.net.URL
import kotlin.reflect.full.memberProperties

//naming case conventions are intentionally violated because reflection is used to build url strings, which are case sensitive
//this also helps with disambiguation between endpoints and payload types
@Suppress("ClassName", "unused", "FunctionName")
object WarframeMarket : Endpoint(null) {
	override val url = "https://api.warframe.market"
	var language = Region.en
	var platform = Platform.pc

	private val httpClient = HttpClient {
		install(JsonFeature) {
			serializer = JacksonSerializer() {

			}
			//TODO: don't initialize nullable types with default value unless explicitly specified
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
		object auctions : Endpoint(v1), Get<Auctions> {
			override suspend fun get() = httpClient.get<PayloadContainer<Auctions>>(url).payload

			object create : Endpoint(auctions), Create<AuctionCreate, Auction> {
				override suspend fun create(payload: AuctionCreate) =
					httpClient.post<PayloadContainer<Auction>>(createRequestBuilder(url, payload)).payload
			}

			object entry : Endpoint(auctions) {
				class ENTRY(auction_id: String) : Endpoint(entry), Get<Auction>, Update<AuctionUpdate, Auction> {
					override val pathName = auction_id

					override suspend fun get() = httpClient.get<PayloadContainer<Auction>>(url).payload

					override suspend fun update(payload: AuctionUpdate) =
						httpClient.put<PayloadContainer<Auction>>(createRequestBuilder(url, payload)).payload

					suspend fun close() =
						httpClient.put<PayloadContainer<AuctionClosed>>(createRequestBuilder("$url/close")).payload

					val bids = Bids(this)

					class Bids internal constructor(parent: Endpoint) : Endpoint(parent), Get<payload.response.Bids> {
						override suspend fun get() = httpClient.get<PayloadContainer<payload.response.Bids>>(url).payload
					}
				}
			}

			object participant : Endpoint(auctions), Get<AuctionsParticipant> {
				override suspend fun get() = httpClient.get<PayloadContainer<AuctionsParticipant>>(url).payload
			}

			object popular : Endpoint(auctions), Get<Auctions> {
				override suspend fun get() = httpClient.get<PayloadContainer<Auctions>>(url).payload
			}

			object search : Endpoint(auctions) {
				suspend fun get(searchParameters: AuctionSearch) = httpClient.get<PayloadContainer<Auctions>>(url) {
					url {
						searchParameters::class.memberProperties.forEach { property ->
							property.getter.call(searchParameters)?.let { value ->
								parameter(property.name, value)
							}
						}
					}
				}.payload
			}
		}

		object auth : Endpoint(v1) {
			suspend fun signIn(payload: SigninCredentials): ProfilePrivate {
				val profile = httpClient.post<PayloadContainer<ProfilePrivate>>(createRequestBuilder("$url/signin", payload)).payload
				jwt = httpClient.cookies("$url/signin").find { it.name == "JWT" }!!.value
				return profile
			}

			suspend fun signOut(): Unit { //returns a profile with all values null
				httpClient.get<HttpResponse>("$url/signout")
				jwt = ""
			}

			suspend fun register(): Nothing = TODO()
		}

		object im : Endpoint(v1) {
			object chats : Endpoint(im), Get<Chats> {
				override suspend fun get() = httpClient.get<PayloadContainer<Chats>>(createRequestBuilder(url)).payload

				class CHAT(chat_id: String) : Endpoint(chats), Get<List<Chats.Chat.Message>>, Delete<ChatDeleted> {
					override val pathName = chat_id

					override suspend fun get() = httpClient.get<PayloadContainer<List<Chats.Chat.Message>>>(createRequestBuilder(url)).payload

					override suspend fun delete() = httpClient.delete<PayloadContainer<ChatDeleted>>(createRequestBuilder(url)).payload
				}
			}

			object ignore : Endpoint(im), Get<List<User>>, Create<IgnoreCreate, IgnoreCreated> {
				override suspend fun get() = httpClient.get<PayloadContainer<List<User>>>(createRequestBuilder(url)).payload

				override suspend fun create(payload: IgnoreCreate) =
					httpClient.post<PayloadContainer<IgnoreCreated>>(createRequestBuilder(url, payload)).payload

				class IGNORE(user_id: String) : Endpoint(im), Delete<IgnoreDeleted> {
					override val pathName = user_id

					override suspend fun delete() = httpClient.delete<PayloadContainer<IgnoreDeleted>>(createRequestBuilder(url)).payload
				}
			}
		}

		object items : Endpoint(v1), Get<Items> {
			override suspend fun get() = httpClient.get<PayloadContainer<Items>>(createRequestBuilder(url)).payload

			class ITEM(url_name: String) : Endpoint(items), Get<Item> {
				override val pathName = url_name

				override suspend fun get() = httpClient.get<PayloadContainer<Item>>(createRequestBuilder(url)).payload

				val orders = Orders(this)
				val statistics = Statistics(this)

				class Orders internal constructor(parent: Endpoint?) : Endpoint(parent), Get<ItemOrders> {
					override suspend fun get() =
						httpClient.get<PayloadContainer<ItemOrders>>(createRequestBuilder(url)).payload

					val top = Top(this)

					class Top internal constructor(parent: Endpoint?) : Endpoint(parent), Get<ItemOrdersTop> {
						override suspend fun get() =
							httpClient.get<PayloadContainer<ItemOrdersTop>>(createRequestBuilder(url)).payload
					}
				}

				class Statistics internal constructor(parent: Endpoint?) : Endpoint(parent), Get<ItemStatistics> {
					override suspend fun get() =
						httpClient.get<PayloadContainer<ItemStatistics>>(createRequestBuilder(url)).payload
				}
			}
		}

		object tools : Endpoint(v1) {
			object ducats : Endpoint(tools), Get<Ducats> {
				override suspend fun get() =
					httpClient.get<PayloadContainer<Ducats>>(createRequestBuilder(url)).payload
			}
		}

		object profile : Endpoint(v1), Get<ProfilePrivate> {
			override suspend fun get() =
				httpClient.get<PayloadContainer<ProfilePrivate>>(createRequestBuilder(url)).payload

			object auctions : Endpoint(profile), Get<Auctions> {
				override suspend fun get() = httpClient.get<PayloadContainer<Auctions>>(url).payload
			}

			object customization : Endpoint(profile) {
				object about : Endpoint(customization), Update<AboutUpdate, About> {
					override suspend fun update(payload: AboutUpdate) =
						httpClient.put<PayloadContainer<About>>(createRequestBuilder(url, payload)).payload
				}

				object avatar : Endpoint(customization), Create<Nothing, ProfilePrivate> {
					override suspend fun create(payload: Nothing) = TODO() //body is a binary image
				}
			}

			object orders : Endpoint(profile), Get<OwnOrders>, Create<OrderCreate, Order> {
				override suspend fun get() =
					httpClient.get<PayloadContainer<OwnOrders>>(createRequestBuilder(url)).payload

				override suspend fun create(payload: OrderCreate) =
					httpClient.post<PayloadContainer<Order>>(createRequestBuilder(url, payload)).payload

				class ORDER(order_id: String) : Endpoint(orders), Update<OrderUpdate, Order>, Delete<OrderDeleted> {
					override val pathName = order_id

					override suspend fun update(payload: OrderUpdate) =
						httpClient.put<PayloadContainer<Order>>(createRequestBuilder(url, payload)).payload

					override suspend fun delete() =
						httpClient.delete<PayloadContainer<OrderDeleted>>(createRequestBuilder(url)).payload

					suspend fun close() =
						httpClient.put<PayloadContainer<OrderClosed>>(createRequestBuilder(orders.url + "/close/" + pathName)).payload
				}
			}

			class USER(username: String) : Endpoint(profile), Get<ProfilePublic> {
				override val pathName = username

				override suspend fun get() =
					httpClient.get<PayloadContainer<ProfilePublic>>(createRequestBuilder(url)).payload

				val achievements = Achievements(this)
				val orders = Orders(this)
				val review = Review(this)
				val reviews = Reviews(this)
				val statistics = Statistics(this)

				class Achievements internal constructor(parent: Endpoint) : Endpoint(parent), Get<UserAchievements> {
					override suspend fun get() =
						httpClient.get<PayloadContainer<UserAchievements>>(createRequestBuilder(url)).payload
				}

				class Orders internal constructor(parent: Endpoint) : Endpoint(parent), Get<UserOrders> {
					override suspend fun get() =
						httpClient.get<PayloadContainer<UserOrders>>(createRequestBuilder(url)).payload
				}

				class Review internal constructor(parent: Endpoint) : Endpoint(parent),
					Create<ReviewCreate, ReviewCreated> {
					override suspend fun create(payload: ReviewCreate) =
						httpClient.post<PayloadContainer<ReviewCreated>>(createRequestBuilder(url, payload)).payload

					fun REVIEW(review_id: String) = REVIEW(review_id, this)

					class REVIEW internal constructor(override val pathName: String, parent: Endpoint?) :
						Endpoint(parent),
						Update<ReviewUpdate, ReviewUpdated>, Delete<ReviewDeleted> {
						override suspend fun update(payload: ReviewUpdate) =
							httpClient.put<PayloadContainer<ReviewUpdated>>(createRequestBuilder(url, payload)).payload

						override suspend fun delete() =
							httpClient.delete<PayloadContainer<ReviewDeleted>>(createRequestBuilder(url)).payload
					}
				}

				class Reviews internal constructor(parent: Endpoint) : Endpoint(parent), Get<payload.response.Reviews> {
					override suspend fun get() =
						httpClient.get<PayloadContainer<payload.response.Reviews>>(createRequestBuilder(url)).payload
				}

				class Statistics internal constructor(parent: Endpoint) : Endpoint(parent), Get<UserStatistics> {
					override suspend fun get() =
						httpClient.get<PayloadContainer<UserStatistics>>(createRequestBuilder(url)).payload
				}
			}
		}

		object settings : Endpoint(v1) {
			object notifications : Endpoint(settings) {
				object push : Endpoint(settings), Create<PushNotificationCreate, String>, Delete<String> {
					override suspend fun create(payload: PushNotificationCreate) =
						httpClient.post<PayloadContainer<String>>(createRequestBuilder(url)).payload

					override suspend fun delete() =
						httpClient.delete<PayloadContainer<String>>(createRequestBuilder(url)).payload
				}
			}

			object verification : Endpoint(settings) {
				suspend fun patch(payload: VerificationPatch) =
					httpClient.patch<PayloadContainer<VerificationPatched>>(
						createRequestBuilder(
							url,
							payload
						)
					).payload
			}
		}
	}
}
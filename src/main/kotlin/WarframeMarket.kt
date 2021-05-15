import com.fasterxml.jackson.annotation.*
import enums.*
import io.ktor.client.HttpClient
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import payload.request.*
import payload.response.*
import payload.response.common.Order
import payload.response.common.User
import kotlin.reflect.full.memberProperties

//naming case conventions are intentionally violated because reflection is used to build url strings, which are case sensitive
//this also helps with disambiguation between endpoints and payload types
@Suppress("ClassName", "unused", "FunctionName")
object WarframeMarket : Endpoint(null) {
	override val url = "https://api.warframe.market"
	var language = Region.en
	var platform = Platform.pc

	override val httpClient = HttpClient {
		install(JsonFeature) {
			serializer = JacksonSerializer {
				setSerializationInclusion(JsonInclude.Include.NON_NULL)
			}
			//TODO: don't initialize nullable types with default value unless explicitly specified
		}
		install(HttpCookies) {
			storage = AcceptAllCookiesStorage()
		}
	}

	object v1 : Endpoint(WarframeMarket) {
		object auctions : Endpoint(v1), Get<Auctions> {
			override suspend fun get() = requestUnwrapped<Auctions>(HttpMethod.Get)

			object create : Endpoint(auctions), Create<AuctionCreate, Auction> {
				override suspend fun create(payload: AuctionCreate) = requestUnwrapped<Auction>(HttpMethod.Post, payload)
			}

			object entry : Endpoint(auctions) {
				class ENTRY(auction_id: String) : Endpoint(entry), Get<Auction>, Update<AuctionUpdate, Auction> {
					override val pathName = auction_id

					override suspend fun get() = requestUnwrapped<Auction>(HttpMethod.Get)
					override suspend fun update(payload: AuctionUpdate) = requestUnwrapped<Auction>(HttpMethod.Delete, payload)
					suspend fun close() = requestUnwrapped<AuctionClosed>(HttpMethod.Put, "$url/close")
					val bids = Bids(this)

					class Bids internal constructor(parent: Endpoint) : Endpoint(parent), Get<payload.response.Bids> {
						override suspend fun get() = requestUnwrapped<payload.response.Bids>(HttpMethod.Get)
					}
				}
			}

			object participant : Endpoint(auctions), Get<AuctionsParticipant> {
				override suspend fun get() = requestUnwrapped<AuctionsParticipant>(HttpMethod.Get)
			}

			object popular : Endpoint(auctions), Get<Auctions> {
				override suspend fun get() = requestUnwrapped<Auctions>(HttpMethod.Get)
			}

			object search : Endpoint(auctions) {
				suspend fun get(searchParameters: AuctionSearch) = requestUnwrapped<AuctionClosed>(HttpMethod.Get) {
					searchParameters::class.memberProperties.forEach { property ->
						property.getter.call(searchParameters)?.let { value ->
							parameter(property.name, value)
						}
					}
				}
			}
		}

		object auth : Endpoint(v1) {
			suspend fun signIn(payload: SigninCredentials) = requestUnwrapped<ProfilePrivate>(HttpMethod.Post, payload, "$url/signin")
			suspend fun signOut() = requestUnwrapped<HttpResponse>(HttpMethod.Get, url = "$url/signout") //returns a profile with all values null
			suspend fun register(): Nothing = TODO()
			suspend fun changePassword(payload: PasswordChange) = requestUnwrapped<String>(HttpMethod.Post, payload, v1.url + "/settings/account/change_password")
		}

		object im : Endpoint(v1) {
			object chats : Endpoint(im), Get<Chats> {
				override suspend fun get() = requestUnwrapped<Chats>(HttpMethod.Get)

				class CHAT(chat_id: String) : Endpoint(chats), Get<List<Chats.Chat.Message>>, Delete<ChatDeleted> {
					override val pathName = chat_id

					override suspend fun get() = requestUnwrapped<List<Chats.Chat.Message>>(HttpMethod.Get)
					override suspend fun delete() = requestUnwrapped<ChatDeleted>(HttpMethod.Delete)
				}
			}

			object ignore : Endpoint(im), Get<List<User>>, Create<IgnoreCreate, IgnoreCreated> {
				override suspend fun get() = requestUnwrapped<List<User>>(HttpMethod.Get)
				override suspend fun create(payload: IgnoreCreate) = requestUnwrapped<IgnoreCreated>(HttpMethod.Post, payload)

				class IGNORE(user_id: String) : Endpoint(im), Delete<IgnoreDeleted> {
					override val pathName = user_id

					override suspend fun delete() = requestUnwrapped<IgnoreDeleted>(HttpMethod.Delete)
				}
			}
		}

		object items : Endpoint(v1), Get<Items> {
			override suspend fun get() = requestUnwrapped<Items>(HttpMethod.Get)

			class ITEM(url_name: String) : Endpoint(items), Get<Item> {
				override val pathName = url_name
				override suspend fun get() = requestUnwrapped<Item>(HttpMethod.Get)
				val orders = Orders(this)
				val statistics = Statistics(this)

				class Orders internal constructor(parent: Endpoint?) : Endpoint(parent), Get<ItemOrders> {
					override suspend fun get() = requestUnwrapped<ItemOrders>(HttpMethod.Get)
					val top = Top(this)

					class Top internal constructor(parent: Endpoint?) : Endpoint(parent), Get<ItemOrdersTop> {
						override suspend fun get() = requestUnwrapped<ItemOrdersTop>(HttpMethod.Get)
					}
				}

				class Statistics internal constructor(parent: Endpoint?) : Endpoint(parent), Get<ItemStatistics> {
					override suspend fun get() = requestUnwrapped<ItemStatistics>(HttpMethod.Get)
				}
			}
		}

		object tools : Endpoint(v1) {
			object ducats : Endpoint(tools), Get<Ducats> {
				override suspend fun get() = requestUnwrapped<Ducats>(HttpMethod.Get)
			}
		}

		object profile : Endpoint(v1), Get<ProfilePrivate> {
			override suspend fun get() = requestUnwrapped<ProfilePrivate>(HttpMethod.Get)

			object auctions : Endpoint(profile), Get<Auctions> {
				override suspend fun get() = requestUnwrapped<Auctions>(HttpMethod.Get)
			}

			object customization : Endpoint(profile) {
				object about : Endpoint(customization), Update<AboutUpdate, About> {
					override suspend fun update(payload: AboutUpdate) = requestUnwrapped<About>(HttpMethod.Delete, payload)
				}

				object avatar : Endpoint(customization), Create<Nothing, ProfilePrivate> {
					override suspend fun create(payload: Nothing) = TODO() //body is a binary image
				}
			}

			object orders : Endpoint(profile), Get<OwnOrders>, Create<OrderCreate, Order> {
				override suspend fun get() = requestUnwrapped<OwnOrders>(HttpMethod.Get)
				override suspend fun create(payload: OrderCreate) = requestUnwrapped<Order>(HttpMethod.Post, payload)

				class ORDER(order_id: String) : Endpoint(orders), Update<OrderUpdate, OrderUpdated>, Delete<OrderDeleted> {
					override val pathName = order_id
					override suspend fun update(payload: OrderUpdate) = requestUnwrapped<OrderUpdated>(HttpMethod.Delete, payload)
					override suspend fun delete() = requestUnwrapped<OrderDeleted>(HttpMethod.Delete)
					suspend fun close() = requestUnwrapped<AuctionClosed>(HttpMethod.Put, orders.url + "/close/" + pathName)
				}
			}

			class USER(username: String) : Endpoint(profile), Get<ProfilePublic> {
				override val pathName = username
				override suspend fun get() = requestUnwrapped<ProfilePublic>(HttpMethod.Get)

				val achievements = Achievements(this)
				val orders = Orders(this)
				val review = Review(this)
				val reviews = Reviews(this)
				val statistics = Statistics(this)

				class Achievements internal constructor(parent: Endpoint) : Endpoint(parent), Get<UserAchievements> {
					override suspend fun get() = requestUnwrapped<UserAchievements>(HttpMethod.Get)
				}

				class Orders internal constructor(parent: Endpoint) : Endpoint(parent), Get<UserOrders> {
					override suspend fun get() = requestUnwrapped<UserOrders>(HttpMethod.Get)
				}

				class Review internal constructor(parent: Endpoint) : Endpoint(parent),
					Create<ReviewCreate, ReviewCreated> {
					override suspend fun create(payload: ReviewCreate) = requestUnwrapped<ReviewCreated>(HttpMethod.Post, payload)
					fun REVIEW(review_id: String) = REVIEW(review_id, this)

					class REVIEW internal constructor(override val pathName: String, parent: Endpoint?) :
						Endpoint(parent),
						Update<ReviewUpdate, ReviewUpdated>, Delete<ReviewDeleted> {
						override suspend fun update(payload: ReviewUpdate) = requestUnwrapped<ReviewUpdated>(HttpMethod.Delete, payload)
						override suspend fun delete() = requestUnwrapped<ReviewDeleted>(HttpMethod.Delete)
					}
				}

				class Reviews internal constructor(parent: Endpoint) : Endpoint(parent), Get<payload.response.Reviews> {
					override suspend fun get() = requestUnwrapped<payload.response.Reviews>(HttpMethod.Get)
				}

				class Statistics internal constructor(parent: Endpoint) : Endpoint(parent), Get<UserStatistics> {
					override suspend fun get() = requestUnwrapped<UserStatistics>(HttpMethod.Get)
				}
			}
		}

		object settings : Endpoint(v1) {
			object notifications : Endpoint(settings) {
				object push : Endpoint(settings), Create<PushNotificationCreate, String>, Delete<String> {
					override suspend fun create(payload: PushNotificationCreate) = requestUnwrapped<String>(HttpMethod.Post, payload)
					override suspend fun delete() = requestUnwrapped<String>(HttpMethod.Delete)
				}
			}

			object verification : Endpoint(settings) {
				suspend fun patch(payload: VerificationPatch) = requestUnwrapped<VerificationPatched>(HttpMethod.Patch, payload)
			}
		}
	}
}
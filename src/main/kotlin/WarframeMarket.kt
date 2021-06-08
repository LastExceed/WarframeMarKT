import enums.*
import io.ktor.client.HttpClient
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import payload.request.*
import payload.response.*
import payload.response.common.UserShortest
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
			serializer = KotlinxSerializer()
		}
		install(HttpCookies) {
			storage = AcceptAllCookiesStorage()
		}
	}

	object v1 : Endpoint(WarframeMarket) {
		object most_recent : Endpoint(v1), Get<RecentOrders> {
			override suspend fun get() = requestUnwrapped<RecentOrders>(HttpMethod.Get)
		}

		object auctions : Endpoint(v1), Get<AuctionsMixed> {
			override suspend fun get() = requestUnwrapped<AuctionsMixed>(HttpMethod.Get)

			object create : Endpoint(auctions), Create<AuctionCreate, AuctionCreated> {
				override suspend fun create(payload: AuctionCreate) = requestUnwrapped<AuctionCreated>(HttpMethod.Post, payload)
			}

			object entry : Endpoint(auctions) {
				class ENTRY(auction_id: String) : Endpoint(entry), Get<AuctionRetrieved>, Update<AuctionUpdate, AuctionCreated> {
					override val pathName = auction_id

					override suspend fun get() = requestUnwrapped<AuctionRetrieved>(HttpMethod.Get)
					override suspend fun update(payload: AuctionUpdate) = requestUnwrapped<AuctionCreated>(HttpMethod.Put, payload)
					suspend fun close() = requestUnwrapped<AuctionClosed>(HttpMethod.Put, url = "$url/close")
					val bids = Bids(this)
					val bans = Bans(this)
					val ban = Ban(this)
					val win = Win(this)

					class Bids internal constructor(parent: Endpoint) : Endpoint(parent), Get<payload.response.Bids> {
						override suspend fun get() = requestUnwrapped<payload.response.Bids>(HttpMethod.Get)
					}

					class Bans internal constructor(parent: Endpoint) : Endpoint(parent), Get<payload.response.Bans> {
						override suspend fun get() = requestUnwrapped<payload.response.Bans>(HttpMethod.Get)
					}

					class Ban internal constructor(parent: Endpoint) : Endpoint(parent) {
						inner class BAN(ban_id: String) : Endpoint(this), Delete<BanDeleted> {
							override val pathName = ban_id
							override suspend fun delete() = requestUnwrapped<BanDeleted>(HttpMethod.Delete)
						}
					}

					class Win internal constructor(parent: Endpoint) : Endpoint(parent), Create<AuctionWin, AuctionCreated> {
						override suspend fun create(payload: AuctionWin) = requestUnwrapped<AuctionCreated>(HttpMethod.Post, payload)
					}
				}
			}

			object popular : Endpoint(auctions), Get<AuctionsMixed> {
				override suspend fun get() = requestUnwrapped<AuctionsMixed>(HttpMethod.Get)
			}

			object search : Endpoint(auctions) {
				suspend fun get(searchParameters: AuctionSearch) = requestUnwrapped<AuctionsExpanded>(HttpMethod.Get) {
					searchParameters::class.memberProperties.forEach { property ->
						property.getter.call(searchParameters)?.let { value ->
							parameter(property.name, if (value is List<*>) value.joinToString(",") else value) //KycKyc plz y u make me do dis
						}
					}
				}
			}
		}

		object auth : Endpoint(v1) {
			suspend fun signIn(payload: SigninCredentials) = requestUnwrapped<ProfilePrivate>(HttpMethod.Post, payload, "$url/signin")
			suspend fun signOut() = requestUnwrapped<ProfilePrivate>(HttpMethod.Get, url = "$url/signout")
			suspend fun register(payload: Registration) = requestUnwrapped<ProfilePrivate>(HttpMethod.Post, payload, "$url/registration")
			suspend fun restore(payload: Restoration) = requestUnwrapped<Unit>(HttpMethod.Post, payload, "$url/restore")
			suspend fun changePassword(payload: PasswordChange) = requestUnwrapped<String>(HttpMethod.Post, payload, v1.url + "/settings/account/change_password")
		}

		object im : Endpoint(v1) {
			object chats : Endpoint(im), Get<Chats>, Create<ChatCreate, ChatRetrieved> {
				override suspend fun get() = requestUnwrapped<Chats>(HttpMethod.Get)
				override suspend fun create(payload: ChatCreate) = requestUnwrapped<ChatRetrieved>(HttpMethod.Post, payload)

				class CHAT(chat_id: String) : Endpoint(chats), Get<Messages>, Delete<ChatRetrieved> {
					override val pathName = chat_id

					override suspend fun get() = requestUnwrapped<Messages>(HttpMethod.Get)
					override suspend fun delete() = requestUnwrapped<ChatRetrieved>(HttpMethod.Delete)
				}
			}

			object ignore : Endpoint(im), Get<List<UserShortest>>, Create<IgnoreCreate, IgnoreCreated> {
				override suspend fun get() = requestUnwrapped<List<UserShortest>>(HttpMethod.Get)
				override suspend fun create(payload: IgnoreCreate) = requestUnwrapped<IgnoreCreated>(HttpMethod.Post, payload)

				class IGNORE(user_id: String) : Endpoint(ignore), Delete<IgnoreDeleted> {
					override val pathName = user_id

					override suspend fun delete() = requestUnwrapped<IgnoreDeleted>(HttpMethod.Delete)
				}
			}
		}

		object items : Endpoint(v1), Get<Items> {
			override suspend fun get() = requestUnwrapped<Items>(HttpMethod.Get)

			class ITEM(url_name: String) : Endpoint(items), Get<ItemRetrieved> {
				override val pathName = url_name
				override suspend fun get() = requestUnwrapped<ItemRetrieved>(HttpMethod.Get)
				val orders = Orders(this)
				val statistics = Statistics(this)

				class Orders internal constructor(parent: Endpoint) : Endpoint(parent), Get<ItemOrders> {
					override suspend fun get() = requestUnwrapped<ItemOrders>(HttpMethod.Get)
					val top = Top(this)

					class Top internal constructor(parent: Endpoint) : Endpoint(parent), Get<ItemOrdersTop> {
						override suspend fun get() = requestUnwrapped<ItemOrdersTop>(HttpMethod.Get)
					}
				}

				class Statistics internal constructor(parent: Endpoint) : Endpoint(parent), Get<ItemStatistics> {
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

				object participant : Endpoint(auctions), Get<AuctionsMixed> {
					override suspend fun get() = requestUnwrapped<AuctionsMixed>(HttpMethod.Get)
				}
			}

			object customization : Endpoint(profile) {
				object about : Endpoint(customization), Create<AboutUpdate, About> {
					override suspend fun create(payload: AboutUpdate) = requestUnwrapped<About>(HttpMethod.Post, payload)
				}

				object avatar : Endpoint(customization), Create<Nothing, ProfilePrivate> {
					override suspend fun create(payload: Nothing) = TODO("not implemented")
				}
			}

			object orders : Endpoint(profile), Get<OwnOrders>, Create<OrderCreate, OrderCreated> {
				override suspend fun get() = requestUnwrapped<OwnOrders>(HttpMethod.Get)
				override suspend fun create(payload: OrderCreate) = requestUnwrapped<OrderCreated>(HttpMethod.Post, payload)

				class ORDER(order_id: String) : Endpoint(orders), Update<OrderUpdate, OrderUpdated>, Delete<OrderDeleted> {
					override val pathName = order_id
					override suspend fun update(payload: OrderUpdate) = requestUnwrapped<OrderUpdated>(HttpMethod.Put, payload)
					override suspend fun delete() = requestUnwrapped<OrderDeleted>(HttpMethod.Delete)
					suspend fun close() = requestUnwrapped<OrderClosed>(HttpMethod.Put, url = orders.url + "/close/" + pathName)
				}
			}

			object statistics : Endpoint(profile) {
				object remove : Endpoint(statistics) {
					class ORDER(order_id: String) : Endpoint(remove), Delete<OrderDeleted> {
						override val pathName = order_id
						override suspend fun delete() = requestUnwrapped<OrderDeleted>(HttpMethod.Delete)
					}
				}
			}

			class USER(username: String) : Endpoint(profile), Get<ProfilePublic> {
				override val pathName = username
				override suspend fun get() = requestUnwrapped<ProfilePublic>(HttpMethod.Get)

				val achievements = Achievements(this)
				val orders = Orders(this)
				val auctions = Auctions(this)
				val review = Review(this)
				val reviews = Reviews(this)
				val statistics = Statistics(this)

				class Achievements internal constructor(parent: Endpoint) : Endpoint(parent), Get<UserAchievements> {
					override suspend fun get() = requestUnwrapped<UserAchievements>(HttpMethod.Get)
				}

				class Orders internal constructor(parent: Endpoint) : Endpoint(parent), Get<UserOrders> {
					override suspend fun get() = requestUnwrapped<UserOrders>(HttpMethod.Get)
				}

				class Auctions internal constructor(parent: Endpoint) : Endpoint(parent), Get<payload.response.Auctions> {
					override suspend fun get() = requestUnwrapped<payload.response.Auctions>(HttpMethod.Get)
				}

				class Review internal constructor(parent: Endpoint) : Endpoint(parent), Create<ReviewCreate, ReviewCreated> {
					override suspend fun create(payload: ReviewCreate) = requestUnwrapped<ReviewCreated>(HttpMethod.Post, payload)

					inner class REVIEW(override val pathName: String) : Endpoint(this), Update<ReviewUpdate, ReviewUpdated>, Delete<ReviewDeleted> {
						override suspend fun update(payload: ReviewUpdate) = requestUnwrapped<ReviewUpdated>(HttpMethod.Put, payload)
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

		object riven : Endpoint(v1) {
			object items : Endpoint(riven), Get<RivenItems> {
				override suspend fun get() = requestUnwrapped<RivenItems>(HttpMethod.Get)
			}

			object attributes : Endpoint(riven), Get<RivenAttributes> {
				override suspend fun get() = requestUnwrapped<RivenAttributes>(HttpMethod.Get)
			}
		}
	}
}
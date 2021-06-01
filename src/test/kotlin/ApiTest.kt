import enums.*
import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assumptions.*
import payload.common.*
import payload.request.*
import kotlin.test.*
import kotlin.test.Test

//currently tests 3 things for each endpoint:
// - the endpoint exists (no 404)
// - the request is valid (200)
// - the response can be deserialized

private fun <T> assertNoExSuspend(block: suspend CoroutineScope.() -> T) = assertDoesNotThrow { runBlocking(block = block) }

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ApiTest {
	var signedIn = false

	@Test
	@BeforeAll
	fun signIn() {
		assertNoExSuspend {
			WarframeMarket.v1.auth.signIn(
				SigninCredentials(
					AuthType.cookie,
					"",
					"", //TODO: provide login credentials somehow
					""
				)
			)
			signedIn = true
		}
	}

	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
	inner class PublicEndpoints {
		@Test
		fun ducats() {
			assertNoExSuspend { WarframeMarket.v1.tools.ducats.get() }
		}

		@Test
		fun achievements() {
			assertNoExSuspend { WarframeMarket.v1.profile.USER("KycKyc").achievements.get() }
		}

		@Test
		fun userOrders() {
			assertNoExSuspend { WarframeMarket.v1.profile.USER("KycKyc").orders.get() }
		}

		@Test
		fun userStatistics() {
			assertNoExSuspend { WarframeMarket.v1.profile.USER("KycKyc").statistics.get() }
		}
	}

	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
	inner class Reviews {
		init {
			assumeTrue(signedIn)
		}

		lateinit var reviewId: IdReview

		@Test
		@Order(1)
		fun create() {
			assertNoExSuspend { reviewId = WarframeMarket.v1.profile.USER("KycKyc").review.create(ReviewCreate("test review", 1)).own_review.id!! }
		}

		@Test
		@Order(2)
		fun get() {
			assertNoExSuspend {
				val reviews = WarframeMarket.v1.profile.USER("KycKyc").reviews.get()
				//TODO: split
				assumeTrue { this@Reviews::reviewId.isInitialized }
				assertEquals(reviews.own_review!!.id, reviewId)
			}
		}

		@Test
		@Order(3)
		fun update() {
			assumeTrue { this::reviewId.isInitialized }
			val reviewUpdated = assertNoExSuspend { WarframeMarket.v1.profile.USER("KycKyc").review.REVIEW(reviewId).update(ReviewUpdate("test review 2")) }
			reviewId = reviewUpdated.review.id!! //this technically isn't necessary as the id doesn't change here, but this is probably a server side bug
		}

		@Test
		@Order(4)
		fun delete() {
			assumeTrue { this::reviewId.isInitialized }
			assertNoExSuspend { WarframeMarket.v1.profile.USER("KycKyc").review.REVIEW(reviewId).delete() }
		}
	}

	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
	inner class Im {
		init {
			assumeTrue(signedIn)
		}

		lateinit var userId: IdUser

		@Test
		@Order(1)
		fun getProfile() {
			val profilePublic = assertNoExSuspend { WarframeMarket.v1.profile.USER("KycKyc").get() }
			userId = profilePublic.profile.id
		}

		lateinit var chatId: IdChat

		@Test
		@Order(2)
		fun chatCreate() {
			assumeTrue(this@Im::userId.isInitialized)
			val chatCreated = assertNoExSuspend { WarframeMarket.v1.im.chats.create(ChatCreate(userId)) }
			chatId = chatCreated.chat_id
		}

		@Test
		@Order(3)
		fun chatShowsUp() {
			val chats = assertNoExSuspend { WarframeMarket.v1.im.chats.get() }
			//TODO: split test here ?
			assumeTrue(this::chatId.isInitialized)
			assertTrue(chats.chats.any { it.id == chatId })
		}

		@Test
		@Order(3)
		fun chatGet() {
			assumeTrue(this::chatId.isInitialized)
			assertNoExSuspend { WarframeMarket.v1.im.chats.CHAT(chatId).get() }
		}

		@Test
		@Order(4)
		fun chatDelete() {
			assumeTrue(this::chatId.isInitialized)
			assertNoExSuspend { WarframeMarket.v1.im.chats.CHAT(chatId).delete() }
		}

		private var ignoreCreated = false

		@Test
		@Order(5)
		fun ignoreCreate() {
			chatCreate() //creating a second time since ignoring requires having a chat open with that user and closes it in the process
			assumeTrue(this::chatId.isInitialized)
			assertNoExSuspend { WarframeMarket.v1.im.ignore.create(IgnoreCreate(userId, chatId)) }
			ignoreCreated = true
		}

		@Test
		@Order(6)
		fun ignoreShowsUp() {
			val ignores = assertNoExSuspend { WarframeMarket.v1.im.ignore.get() }
			//TODO split test here ?
			assumeTrue(ignoreCreated)
			assertTrue(ignores.any { it.id == userId })
		}

		@Test
		@Order(7)
		fun ignoreDelete() {
			assumeTrue(ignoreCreated)
			assertNoExSuspend { WarframeMarket.v1.im.ignore.IGNORE(userId).delete() }
		}
	}

	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
	inner class Auctions {
		@Test
		fun popular() {
			assertNoExSuspend {
				WarframeMarket.v1.auctions.popular.get()
			}
		}

		@Test
		fun searchRiven() {
			assertNoExSuspend {
				WarframeMarket.v1.auctions.search.get(
					AuctionSearchRiven(
						BuyOutPolicy.direct,
						SortingStyle.positive_attr_asc,
						"kuva_bramma",
						listOf(RivenAttributeName.critical_damage),
						listOf(RivenAttributeName.damage_vs_corpus),
						8,
						16,
						0,
						null,
						ModRank.maxed,
						Polarity.any
					)
				)
			}
		}

		@Test
		fun searchLich() {
			assertNoExSuspend {
				WarframeMarket.v1.auctions.search.get(
					AuctionSearchLich(
						BuyOutPolicy.direct,
						SortingStyle.positive_attr_asc,
						"kuva_bramma",
						Element.cold,
						true
					)
				)
			}
		}

		lateinit var auctionId: IdAuction

		@Test
		@Order(1)
		fun create() {
			assumeTrue(signedIn)

			val auctionEntry = assertNoExSuspend {
				WarframeMarket.v1.auctions.create.create(
					AuctionCreate(
						999,
						9999,
						0,
						false,
						"test auction",
						AuctionItemRiven(
							"kuva_bramma",
							"Visibin",
							listOf(
								AuctionItemRiven.Attribute(RivenAttributeName.ammo_maximum, true, 0.1f),
								AuctionItemRiven.Attribute(RivenAttributeName.base_damage_OR_melee_damage, true, 0.2f)
							),
							16,
							8,
							42,
							Polarity.madurai
						)
					)
				)
			}
			auctionId = auctionEntry.auction.id
		}

		@Test
		@Order(2)
		fun showsUpPublic() {
			val auctions = assertNoExSuspend { WarframeMarket.v1.auctions.get() }
			//TODO: split here
			assumeTrue(this::auctionId.isInitialized)
			assertTrue(auctions.auctions.any { it.id == auctionId })
		}

		@Test
		@Order(2)
		fun showsUpPrivate() {
			val auctions = assertNoExSuspend { WarframeMarket.v1.profile.auctions.get() }
			//TODO: split here
			assumeTrue(this::auctionId.isInitialized)
			assertTrue(auctions.auctions.any { it.id == auctionId })
		}

		@Test
		@Order(2)
		fun get() {
			assumeTrue(this::auctionId.isInitialized)
			assertNoExSuspend {
				WarframeMarket.v1.auctions.entry.ENTRY(auctionId).get()
			}
		}

		@Test
		@Order(2)
		fun getBids() {
			assumeTrue(this::auctionId.isInitialized)
			assertNoExSuspend {
				WarframeMarket.v1.auctions.entry.ENTRY(auctionId).bids.get()
			}
		}

		@Test
		@Order(3)
		fun update() {
			assumeTrue(this::auctionId.isInitialized)
			assertNoExSuspend {
				WarframeMarket.v1.auctions.entry.ENTRY(auctionId).update(
					AuctionUpdate(
						888,
						8888,
						1,
						true,
						"test update"
					)
				)
			}
		}

		@Test
		@Order(4)
		fun close() {
			assumeTrue(this::auctionId.isInitialized)
			assertNoExSuspend {
				WarframeMarket.v1.auctions.entry.ENTRY(auctionId).close()
			}
		}
	}

	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
	inner class ItemsAndOrders {
		@Test
		fun items() {
			assertNoExSuspend { WarframeMarket.v1.items.get() } //TODO: contains ?
		}

		lateinit var itemId: IdItem

		@Test
		@Order(1)
		fun item() { //TODO: get ItemUrlName generically
			val item = assertNoExSuspend { WarframeMarket.v1.items.ITEM("maiming_strike").get() }
			itemId = item.item.id
		}

		@Test
		@Order(2)
		fun orders() {
			assumeTrue(this::itemId.isInitialized)
			assertNoExSuspend { WarframeMarket.v1.items.ITEM("maiming_strike").orders.get() }
		}

		@Test
		@Order(2)
		fun ordersTop() {
			assertNoExSuspend { WarframeMarket.v1.items.ITEM("maiming_strike").orders.top.get() }
		}

		@Test
		@Order(2)
		fun statistics() {
			assertNoExSuspend { WarframeMarket.v1.items.ITEM("maiming_strike").statistics.get() }
		}

		lateinit var orderId: IdOrder

		@Test
		@Order(2)
		fun create() {
			assumeTrue(signedIn)
			assumeTrue(this::itemId.isInitialized)
			val orderCreated = assertNoExSuspend {
				WarframeMarket.v1.profile.orders.create(
					OrderCreate(
						OrderType.sell,
						itemId,
						999,
						1,
						3
					)
				)
			}
			orderId = orderCreated.order.id
		}

		@Test
		@Order(3)
		fun update() {
			assumeTrue(this::orderId.isInitialized)
			val orderUpdated = assertNoExSuspend {
				WarframeMarket.v1.profile.orders.ORDER(orderId).update(
					OrderUpdate(
						orderId,
						false,
						888,
						2,
						4
					)
				)
			}
			orderId = orderUpdated.order_id
		}

		//@Test //TODO: endpoint broken
		fun showsUp() {
			val ownOrders = assertNoExSuspend { WarframeMarket.v1.profile.orders.get() }
			//TODO: split
			assumeTrue(this::orderId.isInitialized)
			assertTrue(ownOrders.orders.any { it.id == orderId }) //broken
		}

		@Test
		@Order(4)
		fun close() {
			assumeTrue(this::orderId.isInitialized)
			assertNoExSuspend { WarframeMarket.v1.profile.orders.ORDER(orderId).close() }
		}

		@Test
		@Order(5)
		fun delete() {
			create()
			assumeTrue(this::orderId.isInitialized)
			assertNoExSuspend { WarframeMarket.v1.profile.orders.ORDER(orderId).delete() }
		}
	}

	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
	inner class Settings {
		//@Test
		fun pushNotificationCreate() {
			assertNoExSuspend {
				WarframeMarket.v1.settings.notifications.push.create(
					PushNotificationCreate(
						PushNotificationCreate.Subscription(
							"no idea what this is",
							PushNotificationCreate.Subscription.Keys(
								"still no idea",
								"stop it"
							)
						),
						"device i guess"
					)
				)
			}
		}

		//@Test //TODO: figure out what this is
		fun pushNotificationDelete() {
			assertNoExSuspend { WarframeMarket.v1.settings.notifications.push.delete() }
		}

		@Test
		fun verificationPatch() {
			assertNoExSuspend {
				WarframeMarket.v1.settings.verification.patch(
					VerificationPatch(
						Platform.pc,
						Region.en
					)
				)
			}
		}

		@Test
		fun updateAboutMe() {
			assertNoExSuspend {
				WarframeMarket.v1.profile.customization.about.create(
					AboutUpdate("Junior student at the Orokin Tryhard Academy of Tau")
				)
			}

		}
	}

	//@Test
	//@Order(Int.MAX_VALUE)
	@AfterAll //TODO: this aint working apparently
	fun signOut() {
		assumeTrue(signedIn)
		assertNoExSuspend { WarframeMarket.v1.auth.signOut() }
	}

	//TODO
	//registration
	//restore
	//change pw
	//avatar create
	//WarframeMarket.v1.profile.get()
}

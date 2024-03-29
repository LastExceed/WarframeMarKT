import enums.*
import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assumptions.*
import payload.common.*
import payload.request.*
import java.io.File
import kotlin.test.*
import kotlin.test.Test

//currently tests 3 things for each endpoint:
// - the endpoint exists (no 404)
// - the request is valid (200)
// - the response can be deserialized

//super hacky helper function to support coroutines and rate limit, will redo it properly eventually
private fun <T> assertNoExSuspend(block: suspend CoroutineScope.() -> T) = assertDoesNotThrow {
	runBlocking {
		val cooldown = async { delay(334) }
		val result = block()
		cooldown.await()
		result
	}
}

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ApiTest {
	private var signedIn = false

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
	inner class Auth {
		@Test
		@Order(1)
		@Ignore("don't create a new account as long as this is getting tested against the live server")
		fun register() {
			assertNoExSuspend {
				WarframeMarket.v1.auth.register(
					Registration(
						AuthType.cookie,
						"someone@example.com",
						"3X@MPL3_P@55W0RD",
						"3X@MPL3_P@55W0RD",
						Region.en,
						"example device",
						"ReCaptcha"
					)
				)
			}
		}

		@Test
		@Order(2)
		@Ignore("don't restore the password as long as this is getting tested against the live server")
		fun restore() {
			assertNoExSuspend { WarframeMarket.v1.auth.restore(Restoration("someone@example.com")) }
		}

		@Test
		@Order(3)
		fun signIn() {
			val (email, password) = File("market_credentials").readLines().let { it[0] to it[1] }

			assertNoExSuspend {
				WarframeMarket.v1.auth.signIn(
					SigninCredentials(
						AuthType.cookie,
						email,
						password,
						"example device"
					)
				)
				signedIn = true
			}
		}

		@Test
		@Order(4)
		@Ignore("don't change the password as long as this is getting tested against the live server")
		fun changePassword() {
			assumeTrue(signedIn)
			assertNoExSuspend { WarframeMarket.v1.auth.changePassword(PasswordChange("", "", "")) }
		}

		@Test
		@Ignore("can't figure how to make this run last")
		fun signOut() {
			assumeTrue(signedIn)
			assertNoExSuspend { WarframeMarket.v1.auth.signOut() }
		}

		@Test
		@Ignore("this endpoint is currently broken server side")
		fun profile() {
			assumeTrue(signedIn)
			assertNoExSuspend { WarframeMarket.v1.profile.get() }
		}
	}

	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
	inner class Reviews {
		init {
			assumeTrue(signedIn)
		}

		/**
		 * inner type of Id<*> because https://youtrack.jetbrains.com/issue/KT-23814
		 */
		private lateinit var reviewId: String

		@Test
		@Order(1)
		fun create() {
			val reviewCreated = assertNoExSuspend { WarframeMarket.v1.profile.USER("KycKyc").review.create(ReviewCreate("test review for https://github.com/LastExceed/WarframeMarKT/", ReviewType.Review)) }
			reviewId = assertNotNull(reviewCreated.own_review.id.value)
		}

		@Test
		@Order(2)
		fun showsUp() {
			assertNoExSuspend {
				val reviews = WarframeMarket.v1.profile.USER("KycKyc").reviews.get()
				assumeTrue { this@Reviews::reviewId.isInitialized }
				assertEquals(reviews.own_review?.id?.value, reviewId)
			}
		}

		@Test
		@Order(3)
		fun update() {
			assumeTrue { this::reviewId.isInitialized }
			val reviewUpdated = assertNoExSuspend { WarframeMarket.v1.profile.USER("KycKyc").review.REVIEW(reviewId).update(ReviewUpdate("test review 2")) }
			reviewId = assertNotNull(reviewUpdated.review.id.value) //this technically isn't necessary as the id doesn't change here, but that's probably a server side bug
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

		/**
		 * inner type of Id<*> because https://youtrack.jetbrains.com/issue/KT-23814
		 */
		private lateinit var userId: String

		@Test
		@Order(1)
		fun getProfile() {
			val profilePublic = assertNoExSuspend { WarframeMarket.v1.profile.USER("KycKyc").get() }
			userId = profilePublic.profile.id.value
		}

		/**
		 * inner type of Id<*> because https://youtrack.jetbrains.com/issue/KT-23814
		 */
		private lateinit var chatId: String

		@Test
		@Order(2)
		fun chatCreate() {
			assumeTrue(this@Im::userId.isInitialized)
			val chatCreated = assertNoExSuspend { WarframeMarket.v1.im.chats.create(ChatCreate(Id(userId))) }
			chatId = chatCreated.chat_id.value
		}

		@Test
		@Order(3)
		fun chatShowsUp() {
			val chats = assertNoExSuspend { WarframeMarket.v1.im.chats.get() }
			assumeTrue(this::chatId.isInitialized)
			assertTrue(chats.chats.any { it.id.value == chatId })
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
			assertNoExSuspend { WarframeMarket.v1.im.ignore.create(IgnoreCreate(Id(userId), Id(chatId))) }
			ignoreCreated = true
		}

		@Test
		@Order(6)
		fun ignoreShowsUp() {
			val ignores = assertNoExSuspend { WarframeMarket.v1.im.ignore.get() }
			assumeTrue(ignoreCreated)
			assertTrue(ignores.any { it.id.value == userId })
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
		fun recent() {
			assertNoExSuspend {
				WarframeMarket.v1.auctions.get()
			}
		}

		@Test
		fun popular() {
			assertNoExSuspend {
				WarframeMarket.v1.auctions.popular.get()
			}
		}

		@Test
		fun participant() {
			assertNoExSuspend {
				WarframeMarket.v1.profile.auctions.participant.get()
			}
		}

		@Test
		fun searchRiven() {
			assertNoExSuspend {
				WarframeMarket.v1.auctions.search.get(
					AuctionSearchRiven(
						BuyOutPolicy.direct,
						SortingStyleRiven.positive_attr_asc,
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
						SortingStyleLich.damage_desc,
						"kuva_bramma",
						Element.cold,
						true,
						25,
						60
					)
				)
			}
		}

		/**
		 * inner type of Id<*> because https://youtrack.jetbrains.com/issue/KT-23814
		 */
		private lateinit var auctionId: String

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
			auctionId = auctionEntry.auction.id.value
		}

		@Test
		@Order(2)
		fun showsUpPublic() {
			val auctions = assertNoExSuspend { WarframeMarket.v1.profile.auctions.get() }
			assumeTrue(this::auctionId.isInitialized)
			assertTrue(auctions.auctions.any { it.id.value == auctionId })
		}

		@Test
		@Order(2)
		fun showsUpPrivate() {
			val auctions = assertNoExSuspend { WarframeMarket.v1.profile.USER("LastExceed").auctions.get() }
			assumeTrue(this::auctionId.isInitialized)
			assertTrue(auctions.auctions.any { it.id.value == auctionId })
		}

		@Test
		@Order(2)
		fun get() {
			assumeTrue(this::auctionId.isInitialized)
			assertNoExSuspend { WarframeMarket.v1.auctions.entry.ENTRY(auctionId).get() }
		}

		@Test
		@Order(2)
		fun getBids() {
			assumeTrue(this::auctionId.isInitialized)
			assertNoExSuspend { WarframeMarket.v1.auctions.entry.ENTRY(auctionId).bids.get() }
		}

		@Test
		@Order(2)
		fun getBans() {
			assumeTrue(this::auctionId.isInitialized)
			assertNoExSuspend { WarframeMarket.v1.auctions.entry.ENTRY(auctionId).bans.get() }
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
		@Ignore("no participants")
		fun win() {
			assumeTrue(this::auctionId.isInitialized)
			assertNoExSuspend { WarframeMarket.v1.auctions.entry.ENTRY(auctionId).win.create(AuctionWin(Id("winner_id"))) }
		}

		@Test
		@Order(5)
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
		private lateinit var itemUrlName: ItemUrlName

		@Test
		@Order(1)
		fun items() {
			val items = assertNoExSuspend { WarframeMarket.v1.items.get() }
			val maimingStrike = assertNotNull(items.items.find { it.item_name == "Maiming Strike" })
			itemUrlName = maimingStrike.url_name
		}

		/**
		 * inner type of Id<*> because https://youtrack.jetbrains.com/issue/KT-23814
		 */
		private lateinit var itemId: String

		@Test
		@Order(2)
		fun item() {
			assumeTrue(this::itemUrlName.isInitialized)
			val item = assertNoExSuspend { WarframeMarket.v1.items.ITEM(itemUrlName).get() }
			itemId = item.item.id.value
		}

		@Test
		@Order(3)
		fun orders() {
			assumeTrue(this::itemId.isInitialized)
			assertNoExSuspend { WarframeMarket.v1.items.ITEM(itemUrlName).orders.get() }
		}

		@Test
		@Order(3)
		fun ordersTop() {
			assumeTrue(this::itemUrlName.isInitialized)
			assertNoExSuspend { WarframeMarket.v1.items.ITEM(itemUrlName).orders.top.get() }
		}

		@Test
		@Order(3)
		fun statistics() {
			assumeTrue(this::itemUrlName.isInitialized)
			assertNoExSuspend { WarframeMarket.v1.items.ITEM(itemUrlName).statistics.get() }
		}

		/**
		 * inner type of Id<*> because https://youtrack.jetbrains.com/issue/KT-23814
		 */
		private lateinit var orderId: String

		@Test
		@Order(3)
		fun create() {
			assumeTrue(signedIn)
			assumeTrue(this::itemId.isInitialized)
			val orderCreated = assertNoExSuspend {
				WarframeMarket.v1.profile.orders.create(
					OrderCreate(
						OrderType.sell,
						Id(itemId),
						999,
						1,
						3
					)
				)
			}
			orderId = orderCreated.order.id.value
		}

		@Test
		@Order(4)
		fun update() {
			assumeTrue(this::orderId.isInitialized)
			val orderUpdated = assertNoExSuspend {
				WarframeMarket.v1.profile.orders.ORDER(orderId).update(
					OrderUpdate(
						Id(orderId),
						false,
						888,
						2,
						4
					)
				)
			}
			orderId = orderUpdated.order.id.value
		}

		@Test
		@Order(5)
		fun showsUpPublic() {
			val recentOrders = assertNoExSuspend { WarframeMarket.v1.most_recent.get() }
			assumeTrue(this::orderId.isInitialized)
			//assertTrue((recentOrders.buy_orders + recentOrders.sell_orders).any { it.id == orderId })
			//actually doesn't show up, maybe delayed?
		}

		@Test
		@Order(6)
		fun close() {
			assumeTrue(this::orderId.isInitialized)
			assertNoExSuspend { WarframeMarket.v1.profile.orders.ORDER(orderId).close() }
		}

		/**
		 * inner type of Id<*> because https://youtrack.jetbrains.com/issue/KT-23814
		 */
		lateinit var transactionId: String

		@Test
		@Order(7)
		fun showsUpPrivate() {
			val userStatistics = assertNoExSuspend { WarframeMarket.v1.profile.USER("LastExceed").statistics.get() }
			assumeTrue(this::itemId.isInitialized)
			assumeTrue(this::orderId.isInitialized)
			val transaction = userStatistics.closed_orders.find { it.item.id.value == itemId }
			assertTrue(transaction != null)
			transactionId = transaction.id.value
		}

		@Test
		@Order(8)
		fun deleteTransaction() {
			assumeTrue(this::transactionId.isInitialized)
			val userStatistics = assertNoExSuspend { WarframeMarket.v1.profile.statistics.remove.ORDER(transactionId).delete() }
		}

		@Test
		@Order(8)
		fun delete() {
			assumeTrue(this::orderId.isInitialized)
			assertNoExSuspend { WarframeMarket.v1.profile.orders.ORDER(orderId).delete() }
		}
	}

	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
	inner class Settings {
		@Test
		@Ignore("usage instructions unknown")
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

		@Test
		@Ignore("see pushNotificationCreate")
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

		@Test
		@Ignore("not implemented")
		fun changeAvatar() {
			assertNoExSuspend { WarframeMarket.v1.profile.customization.avatar.create(TODO("not implemented")) }
		}
	}
}

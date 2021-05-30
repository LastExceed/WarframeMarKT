package payload.request

import kotlinx.serialization.Serializable

@Serializable
data class PushNotificationCreate(
	val subscription: Subscription,
	val device: String
) {
	@Serializable
	data class Subscription(
		val endpointeger: String,
		val keys: Keys
	) {
		@Serializable
		data class Keys(
			val p256dh: String,
			val auth: String
		)
	}
}
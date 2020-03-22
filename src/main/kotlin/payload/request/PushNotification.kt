package payload.request

data class PushNotification(
	val subscription: String,
	val device: String
)
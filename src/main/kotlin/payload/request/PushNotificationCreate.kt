package payload.request

data class PushNotificationCreate(
	val subscription: String,
	val device: String
)
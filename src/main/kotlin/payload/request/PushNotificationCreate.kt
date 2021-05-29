package payload.request

import kotlinx.serialization.Serializable

@Serializable
data class PushNotificationCreate(
	val subscription: String,
	val device: String
)
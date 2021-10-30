package payload.response

import enums.*
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import payload.response.common.*

@Serializable
data class Chats private constructor(
	val chats: List<Chat>
) {
	@Serializable
	data class Chat private constructor(
		override val id: Id<Chat>,
		val last_update: Instant,
		val chat_with: List<UserShort>,
		val messages: List<Message>,
		val unread_count: Int,
		val chat_name: String//username
	) : IdCarrier {
		@Serializable
		data class Message private constructor(
			override val id: Id<Message>,
			val message: String,
			val message_from: Id<User>,
			val send_date: Instant,
			val chat_id: Id<Chat>,
			val raw_message: String? = null
		) : IdCarrier
	}
}
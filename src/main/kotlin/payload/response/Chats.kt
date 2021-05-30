package payload.response

import enums.IdChat
import enums.IdMessage
import enums.IdUser
import kotlinx.datetime.*
import payload.response.common.UserShort
import kotlinx.serialization.Serializable

@Serializable
data class Chats private constructor(val chats: List<Chat>) {

	@Serializable
	data class Chat private constructor(
		val last_update: Instant,
		val chat_with: List<UserShort>,
		val messages: List<Message>,
		val unread_count: Int,
		val chat_name: String,//username
		val id: IdChat
	) {
		@Serializable
		data class Message private constructor(
			val message: String,
			val message_from: IdUser,
			val send_date: Instant,
			val chat_id: IdChat,
			val id: IdMessage,
			val raw_message: String? = null
		)
	}
}
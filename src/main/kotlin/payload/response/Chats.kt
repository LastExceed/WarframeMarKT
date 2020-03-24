package payload.response

import enums.IdChat
import enums.IdMessage
import enums.IdUser
import payload.response.common.User
import java.util.*

data class Chats private constructor(val chats: List<Chat>) {
	data class Chat private constructor(
		val last_update: Date,
		val chat_with: List<User>,
		val messages: List<Message>,
		val unread_count: Int,
		val chat_name: String,//username
		val id: IdChat
	) {
		data class Message private constructor(
			val message: String,
			val message_from: IdUser,
			val send_date: Date,
			val chat_id: IdChat,
			val id: IdMessage,
			val raw_message: String
		)
	}
}
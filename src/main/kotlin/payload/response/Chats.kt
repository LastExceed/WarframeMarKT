package payload.response

import payload.response.common.User

data class Chats private constructor(val chats: List<Chat>) {
	data class Chat private constructor(
		val last_update: String,//2019-07-10T18:49:20.801+00:00
		val chat_with: List<User>,
		val messages: List<Message>,
		val unread_count: Int,
		val chat_name: String,//LastExceed
		val id: String//5d26332f92d6d7004e5f048e
	) {
		data class Message private constructor(
			val message: String,//<p>LastExceed Hi! I want to buy: rifle riven mod (veiled) for 60 platinum.</p>
			val message_from: String,//5d1649bdf892d5007494c6a3
			val send_date: String,//2019-07-10T18:49:20.821+00:00
			val chat_id: String,//5d26332f92d6d7004e5f048e
			val id: String,//5d2633305f3ddc00166c2797
			val raw_message: String//LastExceed Hi! I want to buy: rifle riven mod (veiled) for 60 platinum.
		)
	}
}
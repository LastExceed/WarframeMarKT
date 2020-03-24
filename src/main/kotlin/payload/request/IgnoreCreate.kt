package payload.request

import enums.IdChat
import enums.IdUser

data class IgnoreCreate(
	val user_id: IdUser,
	val chat_id: IdChat
)
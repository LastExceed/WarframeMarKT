package payload.request

import enums.IdUser
import kotlinx.serialization.Serializable

@Serializable
data class ChatCreate(
	val user_id: IdUser
)

package payload.request

import enums.*
import kotlinx.serialization.Serializable

@Serializable
data class ChatCreate(val user_id: IdUser)

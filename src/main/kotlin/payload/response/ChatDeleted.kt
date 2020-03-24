package payload.response

import enums.IdChat

data class ChatDeleted private constructor(val chat_id: IdChat)
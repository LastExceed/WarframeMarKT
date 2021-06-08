package payload.response

import enums.IdBan
import kotlinx.serialization.Serializable

@Serializable
data class BanDeleted private constructor(
	val ban_id: IdBan
)

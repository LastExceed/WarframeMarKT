package payload.response

import enums.Id
import kotlinx.serialization.Serializable

@Serializable
data class BanDeleted private constructor(
	val ban_id: Id<Bans.Ban>
)

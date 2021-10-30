package payload.request

import enums.Id
import kotlinx.serialization.Serializable
import payload.response.common.User

@Serializable
data class AuctionWin(
	val user_id: Id<User>
)

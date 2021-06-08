package payload.request

import enums.IdUser
import kotlinx.serialization.Serializable

@Serializable
data class AuctionWin(
	val user_id: IdUser
)

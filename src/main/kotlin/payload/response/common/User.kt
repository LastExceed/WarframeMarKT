package payload.response.common

import enums.IdUser
import enums.Region
import enums.ResourceLocation
import enums.Status

data class User private constructor(
	var status: Status?,
	var ingame_name: String,
	var region: Region,
	var avatar: ResourceLocation?,
	var id: IdUser,
	var reputation: Double,
	var last_seen: String? = null, //missing in Chats
	var reputation_bonus: Double?
)
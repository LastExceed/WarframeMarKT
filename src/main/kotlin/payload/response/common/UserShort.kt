package payload.response.common

import enums.IdUser
import enums.Region
import enums.ResourceLocation
import enums.Status
import kotlinx.serialization.Serializable

@Serializable
data class UserShort private constructor(
	var status: Status? = null,//missing in ignorecreate
	var ingame_name: String,
	var region: Region,
	var avatar: ResourceLocation?,
	var id: IdUser,
	var reputation: Double,
	var last_seen: String? = null, //missing in Chats
)
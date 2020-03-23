package payload.response.common

data class User private constructor(
	var status: String?,
	var ingame_name: String,
	var region: String,
	var avatar: String?,
	var id: String,
	var reputation: Double,
	var last_seen: String? = null, //missing in Chats
	var reputation_bonus: Double?
)
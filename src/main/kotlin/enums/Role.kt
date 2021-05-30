package enums

import kotlinx.serialization.Serializable

@Serializable
enum class Role {
	anonymous,
	user,
	moderator,
	admin
}
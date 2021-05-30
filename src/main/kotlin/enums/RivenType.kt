package enums

import kotlinx.serialization.Serializable

@Serializable
enum class RivenType {
	shotgun,
	rifle,
	pistol,
	melee,
	zaw,
	kitgun
}
package enums

import kotlinx.serialization.Serializable

@Serializable
enum class WeaponGroup {
	primary,
	secondary,
	melee,
	zaw,
	sentinel,
	archgun,
	kitgun
}
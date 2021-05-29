package enums

import kotlinx.serialization.Serializable

@Serializable
enum class Rarity {
	common,
	uncommon,
	rare,
	legendary
}
package enums

import kotlinx.serialization.Serializable

@Serializable
enum class OrderType {
	buy,
	sell
}
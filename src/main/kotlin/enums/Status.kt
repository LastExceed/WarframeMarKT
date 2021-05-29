package enums

import kotlinx.serialization.Serializable

@Serializable
enum class Status {
	offline,
	online,
	ingame
}
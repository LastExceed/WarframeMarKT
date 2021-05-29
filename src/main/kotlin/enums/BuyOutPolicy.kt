package enums

import kotlinx.serialization.Serializable

@Serializable
enum class BuyOutPolicy {
	with,
	direct
}
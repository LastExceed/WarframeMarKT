package enums

import kotlinx.serialization.Serializable

@Serializable
enum class AuthType {
	cookie,
	header
}
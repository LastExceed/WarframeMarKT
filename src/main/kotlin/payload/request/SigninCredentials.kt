package payload.request

import enums.AuthType
import kotlinx.serialization.Serializable

@Serializable
data class SigninCredentials(
	val auth_type: AuthType,
	val email: String,
	val password: String,
	val device_id: String
)
package payload.request

import enums.*
import kotlinx.serialization.Serializable

@Serializable
data class Registration(
	val auth_type: AuthType,
	val email: String,
	val password: String,
	val password_second: String,
	val region: Region,
	val device_id: String,
	val recaptcha: String
)

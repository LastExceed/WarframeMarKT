package payload.request

import kotlinx.serialization.Serializable

@Serializable
data class PasswordChange(
	val current_password: String,
	val new_password: String,
	val new_password_second: String
)
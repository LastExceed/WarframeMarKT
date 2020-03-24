package payload.request

data class PasswordChange(
	val current_password: String,
	val new_password: String,
	val new_password_second: String
)
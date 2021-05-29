package payload.request

import kotlinx.serialization.Serializable

@Serializable
data class SigninCredentials(
	val email: String,
	val password: String
)
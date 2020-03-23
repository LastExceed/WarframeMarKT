package payload.response.common

data class Achievement private constructor(
	val name: String,
	val icon: String,
	val description: String,
	val exposed: Boolean,
	val type: String
)
package payload.response.common

data class Achievement(
	val name: String,
	val icon: String,
	val description: String,
	val exposed: Boolean,
	val type: String
)
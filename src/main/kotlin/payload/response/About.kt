package payload.response
import kotlinx.serialization.Serializable

@Serializable
data class About private constructor(
	val about: String,
	val about_raw: String
)
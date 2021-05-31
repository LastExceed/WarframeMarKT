package payload.response.common

import enums.ResourceLocation
import kotlinx.serialization.Serializable

@Serializable
data class Achievement private constructor(
	val name: String,
	val icon: ResourceLocation,
	val description: String,
	val exposed: Boolean,
	val type: String
)
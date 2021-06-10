package payload.response.common

import enums.*
import kotlinx.serialization.Serializable

@Serializable
data class Achievement private constructor(
	val name: String,
	val icon: ResourceLocation,
	val description: String,
	val exposed: Boolean,
	val type: AchievementType
)
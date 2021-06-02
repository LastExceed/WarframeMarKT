package payload.response

import payload.response.common.Achievement
import kotlinx.serialization.Serializable

@Serializable
data class UserAchievements private constructor(
	val achievements: List<Achievement>
)
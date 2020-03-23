package payload.response

import payload.response.common.Achievement

data class UserAchievements private constructor(val achievements: List<Achievement>)
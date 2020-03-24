package payload.response.common

import enums.ResourceLocation

data class Achievement private constructor(
	val name: String,
	val icon: ResourceLocation,
	val description: String,
	val exposed: Boolean,
	val type: String//enum?
)
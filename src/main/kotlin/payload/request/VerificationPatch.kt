package payload.request

import enums.Platform
import enums.Region
import kotlinx.serialization.Serializable

@Serializable
data class VerificationPatch(
	val platform: Platform,
	val region: Region
)
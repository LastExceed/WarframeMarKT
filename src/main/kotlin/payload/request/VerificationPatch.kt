package payload.request

import enums.*
import kotlinx.serialization.Serializable

@Serializable
data class VerificationPatch(
	val platform: Platform,
	val region: Region
)
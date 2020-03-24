package payload.request

import enums.Platform
import enums.Region

data class VerificationPatch(
	val platform: Platform,
	val region: Region
)
package payload.response

import kotlinx.serialization.Serializable

@Serializable
data class VerificationPatched private constructor(val modified_count: Int)
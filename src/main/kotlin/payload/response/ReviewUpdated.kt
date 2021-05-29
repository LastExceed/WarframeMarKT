package payload.response

import payload.response.common.Review
import kotlinx.serialization.Serializable

@Serializable
data class ReviewUpdated private constructor(val review: Review)
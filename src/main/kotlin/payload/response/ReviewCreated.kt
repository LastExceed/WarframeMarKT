package payload.response

import payload.response.common.Review
import kotlinx.serialization.Serializable

@Serializable
data class ReviewCreated private constructor(val own_review: Review)
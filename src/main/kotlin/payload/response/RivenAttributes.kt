package payload.response

import enums.*
import kotlinx.serialization.Serializable

@Serializable
data class RivenAttributes private constructor(
	val attributes: List<RivenAttribute>
) {
	@Serializable
	data class RivenAttribute(
		override val id: Id<RivenAttribute>,
		val url_name: RivenAttributeName,
		val group: RivenAttributeGroup,
		val prefix: String?,
		val suffix: String?,
		val positive_is_negative: Boolean,
		val exclusive_to: List<RivenType>? = null, //missing if available to all types
		val effect: String,
		val units: RivenAttributeUnit? = null,
		val negative_only: Boolean,
		val search_only: Boolean
	) : IdCarrier
}
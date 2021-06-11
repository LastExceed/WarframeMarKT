package enums

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(ReviewTypeAsIntSerializer::class)
enum class ReviewType(val serializedForm: Int) {
	Review(1),
	Report(-1);

	//workaround for https://github.com/Kotlin/kotlinx.serialization/issues/1386
	//pretty hacky as the docs explicitly state that this doesn't work, but it does (for now)
	//only matters for serializing a review type DIRECTLY (which is never required for interfacing with the wf.m api anyway)
	//this gets fixed in the next version of the kotlin compiler so it's not worth the time looking for a better workaround
	companion object {
		fun serializer() = ReviewTypeAsIntSerializer
	}
}

object ReviewTypeAsIntSerializer : KSerializer<ReviewType> {
	override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Fruit", PrimitiveKind.INT)

	override fun serialize(encoder: Encoder, value: ReviewType) {
		encoder.encodeInt(value.serializedForm)
	}

	override fun deserialize(decoder: Decoder): ReviewType {
		val value = decoder.decodeInt()
		return ReviewType.values().first { it.serializedForm == value }
	}
}
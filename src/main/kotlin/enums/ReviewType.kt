package enums

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Serializable(ReviewTypeAsIntSerializer::class)
enum class ReviewType(val serializedForm: Int) {
	Review(1),
	Report(-1)
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
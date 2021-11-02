package enums

import kotlinx.serialization.Serializable

typealias ItemUrlName = String
typealias ResourceLocation = String

interface IdCarrier {
	val id: Id<out IdCarrier> //kotlin doesn't have a Self type so this is the best we can do
}

@JvmInline
@Serializable
value class Id<T : IdCarrier>(val value: String)
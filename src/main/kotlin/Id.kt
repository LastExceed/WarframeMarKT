import kotlinx.serialization.Serializable

interface IdCarrier {
	val id: Id<out IdCarrier> //kotlin doesn't have a Self type so this is the best we can do
}

@JvmInline
@Serializable
value class Id<T : IdCarrier>(val value: String)
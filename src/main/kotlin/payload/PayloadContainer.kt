package payload

import kotlinx.serialization.Serializable

@Serializable
data class PayloadContainer<PayloadType> public constructor(
	val payload: PayloadType
)
package payload

data class PayloadContainer<PayloadType> internal constructor(val payload: PayloadType)
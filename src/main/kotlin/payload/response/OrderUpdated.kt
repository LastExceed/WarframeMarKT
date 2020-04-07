package payload.response

import payload.response.common.Order

data class OrderUpdated private constructor(val order: Order)
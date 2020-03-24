package payload.response

import enums.IdOrder

data class OrderDeleted private constructor(val order_id: IdOrder)
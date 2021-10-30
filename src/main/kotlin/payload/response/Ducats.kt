package payload.response

import enums.*
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Ducats private constructor(
	val previous_hour: List<Entry>,
	val previous_day: List<Entry>
) {
	@Serializable
	data class Entry private constructor(
		override val id: Id<Entry>,
		val datetime: Instant,
		val position_change_month: Int,
		val position_change_week: Int,
		val position_change_day: Int,
		val plat_worth: Float,
		val volume: Int,
		val ducats_per_platinum: Float,
		val ducats_per_platinum_wa: Float,
		val ducats: Int,
		val item: String,//IdItem? ItemUrlName?
		val median: Float,
		val wa_price: Float
	) : IdCarrier
}
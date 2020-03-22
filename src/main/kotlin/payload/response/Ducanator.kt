package payload.response

data class Ducanator(
	val previous_hour: List<Entry>,
	val previous_day: List<Entry>
) {
	data class Entry(
		val datetime: String,
		val position_change_month: Int,
		val position_change_week: Int,
		val position_change_day: Int,
		val plat_worth: Float,
		val volume: Int,
		val ducats_per_platinum: Float,
		val ducats_per_platinum_wa: Float,
		val ducats: Int,
		val item: String,
		val median: Float,
		val wa_price: Float,
		val id: String
	)
}
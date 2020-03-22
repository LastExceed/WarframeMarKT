package payload.response

data class ItemStatistics(
	val statistics_closed: Statistics<Statistics.Closed>,
	val statistics_live: Statistics<Statistics.Live>
) {
	data class Statistics<T>(
		val `90days`: List<T>,
		val `48hours`: List<T>
	) {
		//TODO: inheritance
		data class Closed(
			val datetime: String,
			val volume: Int,
			val min_price: Float,
			val max_price: Float,
			val avg_price: Float,
			val wa_price: Float,
			val median: Float,
			val mod_rank: Int?,
			val id: String,
			val moving_avg: Float,

			val open_price: Float,//closed only
			val closed_price: Float,//closed only
			val donch_top: Float,//closed only
			val donch_bot: Float//closed only
		)

		data class Live(
			val datetime: String,
			val volume: Int,
			val min_price: Float,
			val max_price: Float,
			val avg_price: Float,
			val wa_price: Float,
			val median: Float,
			val mod_rank: Int,
			val id: String,
			val moving_avg: Float,

			val order_type: String//live only
		)
	}
}
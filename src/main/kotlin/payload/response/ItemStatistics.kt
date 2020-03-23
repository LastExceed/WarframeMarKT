package payload.response

data class ItemStatistics private constructor(
	val statistics_closed: Statistics<Statistics.Closed>,
	val statistics_live: Statistics<Statistics.Live>
) {
	data class Statistics<T> private constructor(
		val `90days`: List<T>,
		val `48hours`: List<T>
	) {
		interface Snapshot {
			val datetime: String
			val volume: Int
			val min_price: Float
			val max_price: Float
			val avg_price: Float
			val wa_price: Float
			val median: Float
			val mod_rank: Int?
			val id: String
			val moving_avg: Float
		}

		data class Closed private constructor(
			override val datetime: String,
			override val volume: Int,
			override val min_price: Float,
			override val max_price: Float,
			override val avg_price: Float,
			override val wa_price: Float,
			override val median: Float,
			override val mod_rank: Int?,
			override val id: String,
			override val moving_avg: Float,

			val open_price: Float,//closed only
			val closed_price: Float,//closed only
			val donch_top: Float,//closed only
			val donch_bot: Float//closed only
		) : Snapshot

		data class Live private constructor(
			override val datetime: String,
			override val volume: Int,
			override val min_price: Float,
			override val max_price: Float,
			override val avg_price: Float,
			override val wa_price: Float,
			override val median: Float,
			override val mod_rank: Int,
			override val id: String,
			override val moving_avg: Float,

			val order_type: String//live only
		) : Snapshot
	}
}
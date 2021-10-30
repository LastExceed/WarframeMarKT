package payload.response

import enums.*
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ItemStatistics private constructor(
	val statistics_closed: Statistics<Statistics.Closed>,
	val statistics_live: Statistics<Statistics.Live>
) {
	@Serializable
	data class Statistics<T> private constructor(
		val `90days`: List<T>,
		val `48hours`: List<T>
	) {
		interface Snapshot : IdCarrier {
			override val id: Id<Snapshot>
			val datetime: Instant
			val volume: Int
			val min_price: Float
			val max_price: Float
			val avg_price: Float
			val wa_price: Float
			val median: Float
			val mod_rank: Int?
			val moving_avg: Float?
		}

		@Serializable
		data class Closed private constructor(
			override val datetime: Instant,
			override val volume: Int,
			override val min_price: Float,
			override val max_price: Float,
			override val avg_price: Float,
			override val wa_price: Float,
			override val median: Float,
			override val mod_rank: Int?,
			override val id: Id<Snapshot>,
			override val moving_avg: Float? = null,

			val open_price: Float,
			val closed_price: Float,
			val donch_top: Float,
			val donch_bot: Float
		) : Snapshot

		@Serializable
		data class Live private constructor(
			override val datetime: Instant,
			override val volume: Int,
			override val min_price: Float,
			override val max_price: Float,
			override val avg_price: Float,
			override val wa_price: Float,
			override val median: Float,
			override val mod_rank: Int,
			override val id: Id<Snapshot>,
			override val moving_avg: Float? = null,

			val order_type: OrderType
		) : Snapshot
	}
}
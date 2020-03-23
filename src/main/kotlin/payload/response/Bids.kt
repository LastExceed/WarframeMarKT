package payload.response

import payload.response.common.User

data class Bids private constructor(val bids: List<Bid>) {
	data class Bid private constructor(
		val value: Int,
		val created: String,//2020-03-23T11:07:52.000+00:00
		val updated: String,//2020-03-23T11:07:52.000+00:00
		val auction: String,//5e7897e4ebde7c048d83a64c
		val user: User,
		val id: String//5e789888880f9200148c9767
	)
}
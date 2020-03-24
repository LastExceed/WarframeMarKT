package payload.request

import enums.*
data class AuctionSearch(
	val type: AuctionType?,
	val weapon_url_name: ItemUrlName?,
	val positive_stats: List<RivenAttribute>?,
	val negative_stats: List<RivenAttribute>?,
	val buyout_policy: BuyOutPolicy?,
	val mastery_rank_min: Int?,
	val mastery_rank_max: Int?,
	val re_rolls_min: Int?,
	val re_rolls_max: Int?,
	val mod_rank: ModRank?,
	val polarity: Polarity?,
	val sort_by: SortingStyle?
)
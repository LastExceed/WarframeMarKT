package payload.request

import enums.*

abstract class AuctionSearch {
	abstract val type: AuctionType?
	abstract val weapon_url_name: ItemUrlName?
	abstract val buyout_policy: BuyOutPolicy?
	abstract val sort_by: SortingStyle?
}

data class AuctionSearchLich(
	override val type: AuctionType?,
	override val buyout_policy: BuyOutPolicy?,
	override val sort_by: SortingStyle?,
	override val weapon_url_name: ItemUrlName?,
	val element: Element?,
	val having_ephemera: Boolean
) : AuctionSearch()

data class AuctionSearchRiven(
	override val type: AuctionType?,
	override val buyout_policy: BuyOutPolicy?,
	override val sort_by: SortingStyle?,
	override val weapon_url_name: ItemUrlName?,
	val positive_stats: List<RivenAttribute>?,
	val negative_stats: List<RivenAttribute>?,
	val mastery_rank_min: Int?,
	val mastery_rank_max: Int?,
	val re_rolls_min: Int?,
	val re_rolls_max: Int?,
	val mod_rank: ModRank?,
	val polarity: Polarity?
) : AuctionSearch()
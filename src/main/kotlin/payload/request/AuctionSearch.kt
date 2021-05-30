package payload.request

import enums.*
import kotlinx.serialization.Serializable

@Serializable
abstract class AuctionSearch(val type: AuctionType) {
	abstract val weapon_url_name: ItemUrlName?
	abstract val buyout_policy: BuyOutPolicy?
	abstract val sort_by: SortingStyle?
}

@Serializable
data class AuctionSearchLich(
	override val buyout_policy: BuyOutPolicy?,
	override val sort_by: SortingStyle?,
	override val weapon_url_name: ItemUrlName?,
	val element: Element?,
	val having_ephemera: Boolean
) : AuctionSearch(AuctionType.lich)

@Serializable
data class AuctionSearchRiven(
	override val buyout_policy: BuyOutPolicy?,
	override val sort_by: SortingStyle?,
	override val weapon_url_name: ItemUrlName?,
	val positive_stats: List<RivenAttributeName>?,
	val negative_stats: List<RivenAttributeName>?,
	val mastery_rank_min: Int?,
	val mastery_rank_max: Int?,
	val re_rolls_min: Int?,
	val re_rolls_max: Int?,
	val mod_rank: ModRank?,
	val polarity: Polarity?
) : AuctionSearch(AuctionType.riven)
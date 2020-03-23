package payload.request

data class AuctionSearch(
	val type: String?,//riven
	val weapon_url_name: String?,//vectis
	val positive_stats: List<String>?,//cold_damage,damage_vs_corpus,critical_chance
	val negative_stats: List<String>?,//magazine_capacity
	val buyout_policy: String?,//with
	val mastery_rank_min: Int?,//8
	val mastery_rank_max: Int?,//13
	val re_rolls_min: Int?,//42
	val re_rolls_max: Int?,//69
	val mod_rank: String?,//maxed
	val polarity: String?,//vazarin
	val sort_by: String?//positive_attr_asc
)
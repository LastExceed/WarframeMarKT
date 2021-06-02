package enums

import kotlinx.serialization.Serializable

//enum inheritance is more confusing than helpful so we'll just use an empty interface and accept duplicating 2 lines
interface SortingStyle

@Serializable
enum class SortingStyleRiven : SortingStyle {
	positive_attr_desc,
	positive_attr_asc,
	price_asc,
	price_desc
}

@Serializable
enum class SortingStyleLich : SortingStyle {
	damage_desc,
	damage_asc,
	price_asc,
	price_desc
}
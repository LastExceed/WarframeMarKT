package enums

import kotlinx.serialization.Serializable

@Serializable
enum class SortingStyle {
	positive_attr_desc,
	positive_attr_asc,
	price_asc,
	price_desc
}
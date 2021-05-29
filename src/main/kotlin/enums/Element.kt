package enums

import kotlinx.serialization.Serializable

@Serializable
enum class Element {
	magnetic,
	heat,
	electricity,
	cold,
	impact,
	toxin,
	radiation
}
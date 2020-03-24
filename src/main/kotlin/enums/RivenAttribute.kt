package enums

import com.fasterxml.jackson.annotation.JsonProperty

@Suppress("EnumEntryName")
enum class RivenAttribute {
	@JsonProperty("base_damage_/_melee_damage")base_damage_OR_melee_damage,
	@JsonProperty("fire_rate_/_attack_speed")`fire_rate_OR_attack_speed`,
	ammo_maximum,
	chance_to_gain_extra_combo_count,
	channeling_damage,
	channeling_efficiency,
	cold_damage,
	combo_duration,
	critical_chance,
	critical_chance_on_slide_attack,
	critical_damage,
	damage_vs_corpus,
	damage_vs_grineer,
	damage_vs_infested,
	electric_damage,
	finisher_damage,
	heat_damage,
	impact_damage,
	magazine_capacity,
	multishot,
	projectile_speed,
	punch_through,
	puncture_damage,
	range,
	recoil,
	reload_speed,
	slash_damage,
	status_chance,
	status_duration,
	toxin_damage,
	zoom,
}
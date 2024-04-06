package com.anirudh.shaadi.data.model

data class Location(
    val city: String = "",
    val coordinates: Coordinates? = null,
    val country: String = "",
    val state: String = "",
    val street: Street? = null,
    val timezone: Timezone? = null
)
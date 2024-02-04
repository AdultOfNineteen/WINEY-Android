package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName

data class TastingNoteFilters(
    @SerializedName("wineTypes")
    val wineTypes: List<WineTypeResponse>,
    @SerializedName("countries")
    val countries: List<WineCountryResponse>
)

data class WineTypeResponse(
    @SerializedName("type")
    val type: String,
    @SerializedName("count")
    var count: Int
)

data class WineCountryResponse(
    @SerializedName("country")
    val country: String,
    @SerializedName("count")
    var count: Int
)

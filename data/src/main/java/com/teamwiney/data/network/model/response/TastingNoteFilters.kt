package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName

data class TastingNoteFilters(
    @SerializedName("wineTypes")
    val wineTypes: List<WineType>,
    @SerializedName("countries")
    val countries: List<WineCountry>
)

data class WineType(
    @SerializedName("type")
    val type: String,
    @SerializedName("count")
    val count: String
)

data class WineCountry(
    @SerializedName("country")
    val country: String,
    @SerializedName("count")
    val count: String
)

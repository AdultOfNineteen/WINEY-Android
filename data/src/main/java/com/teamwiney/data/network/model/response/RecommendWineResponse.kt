package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName

data class RecommendWineResponse(
    @SerializedName("wineId")
    val wineId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("varietal")
    val varietal: String
)

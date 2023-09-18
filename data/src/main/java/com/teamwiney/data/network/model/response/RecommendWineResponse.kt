package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName

data class RecommendWineResponse(
    @SerializedName("wineId")
    val wineId: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("varietal")
    val varietal: List<String>,
    @SerializedName("price")
    val price: Int
)

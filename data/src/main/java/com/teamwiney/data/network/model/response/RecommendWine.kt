package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName

data class RecommendWine(
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

fun RecommendWine.toDomain() = RecommendWine(
    wineId = this.wineId,
    name = this.name,
    country = this.country,
    type = this.type,
    varietal = this.varietal,
    price = this.price
)
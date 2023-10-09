package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName

data class Wine(
    @SerializedName("wineId")
    val wineId: Long,
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("varietal")
    val varietal: String,
    @SerializedName("sweetness")
    val sweetness: Int,
    @SerializedName("acidity")
    val acidity: Int,
    @SerializedName("body")
    val body: Int,
    @SerializedName("tannins")
    val tannins: Int,
    @SerializedName("wineSummary")
    val wineSummary: WineSummary
)

fun Wine.toDomain() = Wine(
    wineId = this.wineId,
    type = convertTypeToColor(this.type),
    name = this.name,
    country = this.country,
    varietal = this.varietal,
    sweetness = this.sweetness,
    acidity = this.acidity,
    body = this.body,
    tannins = this.tannins,
    wineSummary = this.wineSummary
)

data class WineSummary(
    @SerializedName("avgPrice")
    val avgPrice: Double,
    @SerializedName("avgSweetness")
    val avgSweetness: Int,
    @SerializedName("avgAcidity")
    val avgAcidity: Int,
    @SerializedName("avgBody")
    val avgBody: Int,
    @SerializedName("avgTannins")
    val avgTannins: Int
)

private fun convertTypeToColor(type: String): String {
    return when(type) {
        "SPARKLING" -> "SPARKL"
        "FORTIFIED" -> "PORT"
        "OTHER" -> "ETC"
        else -> type
    }
}

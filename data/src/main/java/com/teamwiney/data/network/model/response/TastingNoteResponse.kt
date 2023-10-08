package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName

data class TastingNoteResponse(
    @SerializedName("noteId")
    val id: Long,
    @SerializedName("wineName")
    val name: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("starRating")
    val starRating: Int,
    @SerializedName("buyAgain")
    val buyAgain: Boolean,
    @SerializedName("wineType")
    val wineType: String
)

fun TastingNoteResponse.toDomain() = TastingNoteResponse(
    id = this.id,
    name = this.name,
    country = this.country,
    starRating = this.starRating,
    buyAgain = this.buyAgain,
    wineType = convertTypeToColor(this.wineType),
)

private fun convertTypeToColor(type: String): String {
    return when(type) {
        "SPARKLING" -> "SPARKL"
        "FORTIFIED" -> "PORT"
        "OTHER" -> "ETC"
        else -> type
    }
}
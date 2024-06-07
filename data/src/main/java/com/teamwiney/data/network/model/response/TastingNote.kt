package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.teamwiney.core.common.model.WineType

data class TastingNote(
    @SerializedName("noteId")
    val id: Long,
    @SerializedName("tastingNoteNo")
    val tastingNoteNo: Int,
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

fun TastingNote.toDomain() = TastingNote(
    id = this.id,
    tastingNoteNo = this.tastingNoteNo,
    name = this.name,
    country = this.country,
    starRating = this.starRating,
    buyAgain = this.buyAgain,
    wineType = this.wineType
)


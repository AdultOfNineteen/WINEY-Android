package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.teamwiney.core.common.model.WineType

data class SearchWine(
    @SerializedName("wineId")
    val wineId: Long,
    @SerializedName("type")
    val type: WineType,
    @SerializedName("country")
    val country: String,
    @SerializedName("name")
    val name: String
) {
    companion object {
        fun default() = SearchWine(
            wineId = 0L,
            type = WineType.RED,
            country = "",
            name = ""
        )
    }
}

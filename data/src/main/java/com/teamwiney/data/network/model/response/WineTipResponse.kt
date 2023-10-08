package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName

data class WineTipResponse(
    @SerializedName("windTipId")
    val id: Long,
    @SerializedName("thumbNail")
    val thumbnail: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
)
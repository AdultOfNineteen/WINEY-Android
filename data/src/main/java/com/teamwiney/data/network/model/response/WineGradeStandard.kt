package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName

data class WineGradeStandard(
    @SerializedName("name")
    val name: String,
    @SerializedName("minCount")
    val minCount: Int,
    @SerializedName("maxCount")
    val maxCount: Int
)

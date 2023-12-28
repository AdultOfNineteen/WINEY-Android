package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.teamwiney.core.common.model.WineGrade

data class WineGradeStandard(
    @SerializedName("name")
    val name: WineGrade,
    @SerializedName("minCount")
    val minCount: Int,
    @SerializedName("maxCount")
    val maxCount: Int
)

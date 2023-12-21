package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.teamwiney.core.common.model.WineGrade

data class UserWineGrade(
    @SerializedName("currentGrade")
    val currentGrade: WineGrade,
    @SerializedName("expectedNextMonthGrade")
    val expectedMonthGrade: WineGrade,
    @SerializedName("threeMonthsNoteCount")
    val threeMonthsNoteCount: Int
)

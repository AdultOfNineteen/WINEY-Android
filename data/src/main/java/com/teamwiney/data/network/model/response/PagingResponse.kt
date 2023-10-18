package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName

data class PagingResponse<out T>(
    @SerializedName("isLast")
    val isLast: Boolean,
    @SerializedName("totalCnt")
    val totalCnt: Long,
    @SerializedName("contents")
    val contents: T
)

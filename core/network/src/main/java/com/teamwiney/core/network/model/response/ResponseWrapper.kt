package com.teamwiney.core.network.model.response

import com.google.gson.annotations.SerializedName

data class ResponseWrapper<out T>(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: T
)

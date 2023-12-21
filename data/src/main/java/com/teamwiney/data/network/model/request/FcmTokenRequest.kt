package com.teamwiney.data.network.model.request

import com.google.gson.annotations.SerializedName

data class FcmTokenRequest(
    @SerializedName("fcmToken")
    val fcmToken: String,
    @SerializedName("deviceId")
    val deviceId: String
)
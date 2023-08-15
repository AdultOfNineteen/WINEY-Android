package com.teamwiney.data.network.model.request

import com.google.gson.annotations.SerializedName

data class SocialLoginRequest(
    @SerializedName("accessToken")
    val accessToken: String
)

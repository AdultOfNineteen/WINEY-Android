package com.teamwiney.core.network.model.request

import com.google.gson.annotations.SerializedName

data class SocialLoginRequest(
    @SerializedName("accessToken")
    val accessToken: String
)

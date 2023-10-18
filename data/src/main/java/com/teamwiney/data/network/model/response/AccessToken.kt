package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("accessToken")
    val accessToken: String
)

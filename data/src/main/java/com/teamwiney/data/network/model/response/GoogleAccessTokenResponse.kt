package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName

data class GoogleAccessTokenResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: String,
    @SerializedName("id_token")
    val idToken: String,
    @SerializedName("token_type")
    val tokenType: String
)


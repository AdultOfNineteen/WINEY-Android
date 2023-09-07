package com.teamwiney.data.network.model.request

import com.google.gson.annotations.SerializedName

data class GoogleAccessTokenRequest(
    @SerializedName("grant_type")
    val grantType: String,
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("client_secret")
    val clientSecret: String,
    @SerializedName("redirect_uri")
    val redirectUri: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("id_token")
    val idToken: String
)


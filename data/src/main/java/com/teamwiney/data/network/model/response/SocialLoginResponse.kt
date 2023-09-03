package com.teamwiney.data.network.model.response

data class SocialLoginResponse(
    val accessToken: String,
    val userId: Int,
    val refreshToken: String
)
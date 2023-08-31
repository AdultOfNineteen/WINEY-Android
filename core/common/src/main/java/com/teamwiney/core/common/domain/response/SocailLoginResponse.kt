package com.teamwiney.core.common.domain.response

data class SocailLoginResponse(
    val accessToken: String,
    val userId: Int,
    val refreshToken: String
)
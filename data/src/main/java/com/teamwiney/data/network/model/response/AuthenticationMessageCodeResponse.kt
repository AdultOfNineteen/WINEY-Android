package com.teamwiney.data.network.model.response

data class AuthenticationMessageCodeResponse(
    val expireAt: String,
    val phoneNumber: String,
    val sentAt: String
)
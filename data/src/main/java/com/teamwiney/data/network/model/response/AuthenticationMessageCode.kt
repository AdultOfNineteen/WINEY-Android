package com.teamwiney.data.network.model.response

data class AuthenticationMessageCode(
    val expireAt: String,
    val phoneNumber: String,
    val sentAt: String
)
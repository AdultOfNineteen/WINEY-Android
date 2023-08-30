package com.teamwiney.data.network.model.response

data class AuthentificationMessageCodeResponse(
    val expireAt: String,
    val phoneNumber: String,
    val sentAt: String
)
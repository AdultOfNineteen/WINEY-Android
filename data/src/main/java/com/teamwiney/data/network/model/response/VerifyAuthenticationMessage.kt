package com.teamwiney.data.network.model.response

data class VerifyAuthenticationMessage(
    val mismatchAttempts: Int,
    val phoneNumber: String,
    val status: String
)
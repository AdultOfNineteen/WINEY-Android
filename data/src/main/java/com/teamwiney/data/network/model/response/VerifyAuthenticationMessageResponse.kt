package com.teamwiney.data.network.model.response

data class VerifyAuthenticationMessageResponse(
    val mismatchAttempts: Int,
    val phoneNumber: String,
    val status: String
)
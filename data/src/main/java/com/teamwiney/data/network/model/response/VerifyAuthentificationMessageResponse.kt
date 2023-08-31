package com.teamwiney.data.network.model.response

data class VerifyAuthentificationMessageResponse(
    val mismatchAttempts: Int,
    val phoneNumber: String,
    val status: String
)
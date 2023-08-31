package com.teamwiney.data.network.model.request

data class PhoneNumberWithVerificationCodeRequest(
    val phoneNumber: String,
    val verificationCode: String
)
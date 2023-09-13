package com.teamwiney.core.common.base

data class ErrorResponse(
    val code: String,
    val isSuccess: Boolean,
    val message: String
)
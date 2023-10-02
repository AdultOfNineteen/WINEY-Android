package com.teamwiney.core.common.base

data class ErrorResponse(
    val isSuccess: Boolean = false,
    val code: String = CommonResponseStatus.INTERNAL_SERVER_ERROR.code,
    val message: String = CommonResponseStatus.INTERNAL_SERVER_ERROR.message
)
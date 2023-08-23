package com.teamwiney.core.common.base

import com.google.gson.annotations.SerializedName

/**
 * 반복되는 Response의 속성을 담은 클래스로 상속해서 사용
 *
 * @property isSuccess
 * @property code
 * @property message
 */
open class ResponseWrapper<out T>(
    @SerializedName("isSuccess")
    val isSuccess: Boolean = false,
    @SerializedName("code")
    val code: String = "",
    @SerializedName("message")
    val message: String = "",
    @SerializedName("result")
    val result: T? = null
)
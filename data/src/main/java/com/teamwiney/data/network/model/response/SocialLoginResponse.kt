package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.teamwiney.core.common.base.BaseResponse

data class SocialLoginResponse(
    @SerializedName("result")
    val result: String
) : BaseResponse()

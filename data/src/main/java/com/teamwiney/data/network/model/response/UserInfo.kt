package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName
import com.teamwiney.core.common.model.UserStatus

data class UserInfo(
    @SerializedName("userId") val userId: Int,
    @SerializedName("status") val status: UserStatus
)

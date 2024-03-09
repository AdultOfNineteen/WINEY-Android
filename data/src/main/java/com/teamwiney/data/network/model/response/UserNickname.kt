package com.teamwiney.data.network.model.response

import com.google.gson.annotations.SerializedName

data class UserNickname(
    @SerializedName("nickname") val nickname: String
)

package com.teamwiney.core.common.model

import com.google.gson.annotations.SerializedName

enum class SocialType {
    @SerializedName("KAKAO")
    KAKAO,

    @SerializedName("GOOGLE")
    GOOGLE,

    @SerializedName("normal")
    normal
}

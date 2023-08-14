package com.teamwiney.core.network.service

import com.google.gson.annotations.SerializedName
import com.teamwiney.core.network.adapter.ApiResult
import com.teamwiney.core.network.model.response.ResponseWrapper
import retrofit2.http.POST
import retrofit2.http.Path

enum class SocialType {
    @SerializedName("KAKAO")
    KAKAO,
    @SerializedName("GOOGLE")
    GOOGLE,
    @SerializedName("normal")
    normal
}

interface AuthService {

    @POST("/login/{social}")
    suspend fun socialLogin(
        @Path("social") social: SocialType
    ) : ApiResult<ResponseWrapper<String>>

}
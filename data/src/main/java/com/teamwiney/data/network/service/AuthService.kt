package com.teamwiney.data.network.service

import com.google.gson.annotations.SerializedName
import com.teamwiney.data.network.adapter.ApiResult
import com.teamwiney.data.network.model.request.SocialLoginRequest
import com.teamwiney.data.network.model.response.SocialLoginResponse
import retrofit2.http.Body
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
        @Path("social") social: SocialType,
        @Body socialLoginRequest: SocialLoginRequest
    ) : ApiResult<SocialLoginResponse>

}
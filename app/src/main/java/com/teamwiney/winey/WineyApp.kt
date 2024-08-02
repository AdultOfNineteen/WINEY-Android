package com.teamwiney.winey

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.teamwiney.core.common.AmplitudeProvider
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WineyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
        AmplitudeProvider.initialize(this)
    }
}
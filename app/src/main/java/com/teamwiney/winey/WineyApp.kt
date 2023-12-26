package com.teamwiney.winey

import android.app.Application
import android.util.Log
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.KakaoSdk
import com.teamwiney.core.common.util.Constants.DEVICE_ID
import com.teamwiney.core.common.util.Constants.FCM_TOKEN
import com.teamwiney.data.repository.persistence.DataStoreRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class WineyApp : Application() {

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    override fun onCreate() {
        super.onCreate()
        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
        fetchAndSetFcmToken()
        fetchAndSetDeviceId()
    }

    private fun fetchAndSetDeviceId() {
        FirebaseInstallations.getInstance().id.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val deviceId = task.result
                setDeviceId(deviceId)
                Log.d("FCM", "Fetching instance id succeed : $deviceId")
            } else {
                Log.w("FCM", "Fetching instance id failed", task.exception)
            }
        }
    }

    private fun fetchAndSetFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                setFcmToken(token)
                Log.d("FCM", "Fetching FCM registration token succeed : $token")
            } else {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
            }
        }
    }

    private fun setDeviceId(deviceId: String?) {
        deviceId?.let {
            runBlocking {
                dataStoreRepository.setStringValue(DEVICE_ID, it)
            }
        }
    }
    private fun setFcmToken(token: String?) {
        token?.let {
            runBlocking {
                dataStoreRepository.setStringValue(FCM_TOKEN, it)
            }
        }
    }
}
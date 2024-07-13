package com.teamwiney.core.common.di

import android.content.Context
import com.amplitude.android.Amplitude
import com.amplitude.android.Configuration
import com.amplitude.android.DefaultTrackingOptions
import com.amplitude.android.utilities.AndroidLoggerProvider
import com.teamwiney.core.common.BuildConfig

object AmplitudeProvider {
    private var amplitude: Amplitude? = null

    fun initialize(context: Context) {
        amplitude = Amplitude(
            Configuration(
                apiKey = BuildConfig.AMPLITUDE_API_KEY,
                context= context,
                defaultTracking = DefaultTrackingOptions.ALL,
                loggerProvider = AndroidLoggerProvider()
            )
        )
    }

    private fun getAmplitude(): Amplitude {
        return amplitude ?: throw IllegalStateException("Amplitude is not initialized")
    }

    fun trackEvent(eventName: String) {
        getAmplitude().track(eventName)
    }
}
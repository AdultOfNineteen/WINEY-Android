package com.teamwiney.core.common.util

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.teamwiney.core.common.R

enum class UpdateStrategy {
    NONE, FORCE, SOFT, ONCE
}

object RemoteConfigUtil {

    private const val AOS_LATEST_VERSION = "aos_latest_version"
    private const val AOS_MINIMUM_VERSION = "aos_minimum_version"
    private const val AOS_UPDATE_STRATEGY = "aos_update_strategy"
    private const val AOS_UPDATE_CONTENT = "aos_update_content"

    var currentVersionCode: Int = 0
    var latestVersionCode: Int = 0
    var minimumVersionCode: Int = 0
    var updateStrategy: UpdateStrategy = UpdateStrategy.NONE
    var updateContent: String = ""

    fun initialize(appVersionCode: Int) {
        currentVersionCode = appVersionCode
        setupRemoteConfig()
    }

    private fun setupRemoteConfig() {
        val remoteConfig = Firebase.remoteConfig
        remoteConfig.setConfigSettingsAsync(remoteConfigSettings { minimumFetchIntervalInSeconds = 0 })
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                updateConfigValues(remoteConfig)
                logConfigValues()
            } else {
                Log.e("RemoteConfigUtil", "Failed to fetch remote config.")
            }
        }
    }

    private fun updateConfigValues(remoteConfig: com.google.firebase.remoteconfig.FirebaseRemoteConfig) {
        minimumVersionCode = remoteConfig.getString(AOS_MINIMUM_VERSION).toIntOrNull() ?: 0
        latestVersionCode = remoteConfig.getString(AOS_LATEST_VERSION).toIntOrNull() ?: 0
        updateStrategy = parseUpdateStrategy(remoteConfig.getString(AOS_UPDATE_STRATEGY).toIntOrNull() ?: 0)
        updateContent = remoteConfig.getString(AOS_UPDATE_CONTENT)
    }

    private fun parseUpdateStrategy(strategyCode: Int): UpdateStrategy {
        return when (strategyCode) {
            1 -> UpdateStrategy.FORCE
            2 -> UpdateStrategy.SOFT
            3 -> UpdateStrategy.ONCE
            else -> UpdateStrategy.NONE
        }
    }

    private fun logConfigValues() {
        Log.d("RemoteConfigUtil", "CURRENT_VERSION_CODE: $currentVersionCode")
        Log.d("RemoteConfigUtil", "MINIMUM_VERSION_CODE: $minimumVersionCode")
        Log.d("RemoteConfigUtil", "LATEST_VERSION_CODE: $latestVersionCode")
        Log.d("RemoteConfigUtil", "UPDATE_STRATEGY: $updateStrategy")
        Log.d("RemoteConfigUtil", "UPDATE_CONTENT: $updateContent")
    }
}
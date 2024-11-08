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
    private const val AOS_LATEST_VERSION_NAME = "aos_latest_version_name"
    private const val AOS_MINIMUM_VERSION = "aos_minimum_version"
    private const val AOS_UPDATE_STRATEGY = "aos_update_strategy"
    private const val AOS_UPDATE_CONTENT = "aos_update_content"

    private var currentVersionCode: Int = 0
    var latestVersionCode: Int = 0
    private var latestVersionName: String = ""
    private var minimumVersionCode: Int = 0
    private var updateStrategy: UpdateStrategy = UpdateStrategy.NONE
    private var updateContent: String = ""

    fun initialize(appVersionCode: Int) {
        currentVersionCode = appVersionCode
        setupRemoteConfig()
    }

    private fun setupRemoteConfig() {
        Firebase.remoteConfig.apply {
            setConfigSettingsAsync(remoteConfigSettings { minimumFetchIntervalInSeconds = 0 })
            setDefaultsAsync(R.xml.remote_config_defaults)
            fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateConfigValues()
                    logConfigValues()
                } else {
                    Log.e("RemoteConfigUtil", "Failed to fetch remote config.")
                }
            }
        }
    }

    private fun updateConfigValues() {
        val remoteConfig = Firebase.remoteConfig
        minimumVersionCode = remoteConfig.getString(AOS_MINIMUM_VERSION).toIntOrNull() ?: 0
        latestVersionCode = remoteConfig.getString(AOS_LATEST_VERSION).toIntOrNull() ?: 0
        latestVersionName = remoteConfig.getString(AOS_LATEST_VERSION_NAME)
        updateStrategy = parseUpdateStrategy(remoteConfig.getString(AOS_UPDATE_STRATEGY).toIntOrNull() ?: 0)
        updateContent = remoteConfig.getString(AOS_UPDATE_CONTENT)
    }

    private fun parseUpdateStrategy(strategyCode: Int) = when (strategyCode) {
        1 -> UpdateStrategy.FORCE
        2 -> UpdateStrategy.SOFT
        3 -> UpdateStrategy.ONCE
        else -> UpdateStrategy.NONE
    }

    private fun logConfigValues() {
        Log.d("RemoteConfigUtil", """
            CURRENT_VERSION_CODE: $currentVersionCode
            MINIMUM_VERSION_CODE: $minimumVersionCode
            LATEST_VERSION_CODE: $latestVersionCode
            LATEST_VERSION_NAME: $latestVersionName
            UPDATE_STRATEGY: $updateStrategy
            UPDATE_CONTENT: $updateContent
        """.trimIndent())
    }

    fun showUpdateDialog(
        onForceUpdate: (String, String) -> Unit,
        onSoftUpdate: (String) -> Unit,
        onOnceUpdate: (String) -> Unit,
        onNormalLaunch: () -> Unit
    ) {
        when {
            currentVersionCode < minimumVersionCode -> {
                onForceUpdate(latestVersionName, updateContent)
            }
            currentVersionCode >= latestVersionCode -> onNormalLaunch()
            else -> {
                when (updateStrategy) {
                    UpdateStrategy.FORCE -> onForceUpdate(latestVersionName, updateContent)
                    UpdateStrategy.SOFT -> onSoftUpdate(latestVersionName)
                    UpdateStrategy.ONCE -> onOnceUpdate(latestVersionName)
                    else -> onNormalLaunch()
                }
            }
        }
    }
}
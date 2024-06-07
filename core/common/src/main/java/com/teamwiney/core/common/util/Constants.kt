package com.teamwiney.core.common.util

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.teamwiney.core.common.BuildConfig

object Constants {
    const val WINEY_DATSTORE = "winey_datastore"

    // PreferenceKeys
    val IS_NOT_FIRST_LAUNCH = booleanPreferencesKey("is_not_first_launch")
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    val FCM_TOKEN = stringPreferencesKey("fcm_token")
    val DEVICE_ID = stringPreferencesKey("device_id")
    val LOGIN_TYPE = stringPreferencesKey("login_type")
    val IS_FIRST_SCROLL = booleanPreferencesKey("is_first_scroll")
    val USER_ID = intPreferencesKey("user_id")

    const val FAQ_URL = "https://www.notion.so/FAQ-1671bf54033440d2aef23189c4754a45?pvs=4"
    const val PRIVACY_POLICY_URL = "${BuildConfig.BASE_URL}/docs/privacy-policy.html"
    const val TERMS_OF_USE_URL = "${BuildConfig.BASE_URL}/docs/service-policy.html"
}
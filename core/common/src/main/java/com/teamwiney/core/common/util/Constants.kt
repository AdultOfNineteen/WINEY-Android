package com.teamwiney.core.common.util

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val WINEY_DATSTORE = "winey_datastore"

    // PreferenceKeys
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    val LOGIN_TYPE = stringPreferencesKey("login_type")
}
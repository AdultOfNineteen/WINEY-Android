package com.teamwiney.data.repository.persistence

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    fun getToken(type: Preferences.Key<String>): Flow<String>
    suspend fun setToken(type: Preferences.Key<String>, value: String)
}
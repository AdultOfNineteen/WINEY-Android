package com.teamwiney.data.repository.persistence

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    fun getIntValue(type: Preferences.Key<Int>): Flow<Int>
    suspend fun setIntValue(type: Preferences.Key<Int>, value: Int)

    fun getStringValue(type: Preferences.Key<String>): Flow<String>
    suspend fun setStringValue(type: Preferences.Key<String>, value: String)

    fun getBooleanValue(type: Preferences.Key<Boolean>): Flow<Boolean>
    suspend fun setBooleanValue(type: Preferences.Key<Boolean>, value: Boolean)

    suspend fun deleteLongValue(type: Preferences.Key<Long>)

    suspend fun deleteStringValue(type: Preferences.Key<String>)
    suspend fun deleteBooleanValue(type: Preferences.Key<Boolean>)
}
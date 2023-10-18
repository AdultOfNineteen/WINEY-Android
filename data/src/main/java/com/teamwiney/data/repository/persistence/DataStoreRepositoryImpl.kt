package com.teamwiney.data.repository.persistence

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreRepositoryImpl(
    private val preferenceDataStore: DataStore<Preferences>,
) : DataStoreRepository {


    override fun getStringValue(type: Preferences.Key<String>): Flow<String> {
        return preferenceDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { prefs ->
                prefs[type] ?: ""
            }
    }

    override suspend fun setStringValue(type: Preferences.Key<String>, value: String) {
        preferenceDataStore.edit { settings ->
            settings[type] = value
        }
    }

    override fun getBooleanValue(type: Preferences.Key<Boolean>): Flow<Boolean> {
        return preferenceDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { prefs ->
                prefs[type] ?: false
            }
    }

    override suspend fun setBooleanValue(type: Preferences.Key<Boolean>, value: Boolean) {
        preferenceDataStore.edit { settings ->
            settings[type] = value
        }
    }

    override suspend fun deleteStringValue(type: Preferences.Key<String>) {
        preferenceDataStore.edit { settings ->
            settings.remove(type)
        }
    }

    override suspend fun deleteBooleanValue(type: Preferences.Key<Boolean>) {
        preferenceDataStore.edit { settings ->
            settings.remove(type)
        }
    }


}
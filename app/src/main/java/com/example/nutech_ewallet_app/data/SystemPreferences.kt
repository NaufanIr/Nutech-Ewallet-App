package com.example.nutech_ewallet_app.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SystemPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun setToken(token: String) {
        dataStore.edit {
            it[TOKEN_KEY] = token
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map {
            it[TOKEN_KEY] ?: ""
        }
    }

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("TOKEN_KEY")

        @Volatile
        private var INSTANCE: SystemPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): SystemPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SystemPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
package com.example.nutech_ewallet_app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.nutech_ewallet_app.data.api.ApiConfig
import com.example.nutech_ewallet_app.data.repository.TransactionRepository
import com.example.nutech_ewallet_app.data.repository.UserRepository

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val preferences = SystemPreferences.getInstance(context.dataStore)
        return UserRepository.getInstance(apiService, preferences)
    }

    fun provideTransactionRepository(context: Context): TransactionRepository {
        val apiService = ApiConfig.getApiService()
        val preferences = SystemPreferences.getInstance(context.dataStore)
        return TransactionRepository.getInstance(apiService, preferences)
    }
}
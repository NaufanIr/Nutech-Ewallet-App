package com.example.nutech_ewallet_app.data.repository

import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.nutech_ewallet_app.data.SystemPreferences
import com.example.nutech_ewallet_app.data.api.ApiService

class TransactionRepository private constructor(
    private val apiService: ApiService,
    private val preferences: SystemPreferences
) {
    //TOKEN VIA DATA STORE
    val token = preferences.getToken().asLiveData()

    suspend fun setToken(token: String) {
        preferences.setToken(token)
    }

    //TRANSACTION DATA FROM API
    fun getBalance(token: String) = liveData {
        emit(apiService.getBalance(token))
    }

    fun getHistoryTransaction(token: String) = liveData {
        emit(apiService.getTransactionHistory(token))
    }

    fun topup(token: String, amount: Int) = liveData {
        emit(apiService.topUp(token, amount))
    }

    fun transfer(token: String, amount: Int) = liveData {
        emit(apiService.transfer(token, amount))
    }

    companion object {
        @Volatile
        private var instance: TransactionRepository? = null
        fun getInstance(
            apiService: ApiService,
            preferences: SystemPreferences,
        ): TransactionRepository = instance ?: synchronized(this) {
            instance ?: TransactionRepository(apiService, preferences)
        }.also { instance = it }
    }
}
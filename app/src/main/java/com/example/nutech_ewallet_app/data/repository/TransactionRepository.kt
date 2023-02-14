package com.example.nutech_ewallet_app.data.repository

import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.nutech_ewallet_app.data.SystemPreferences
import com.example.nutech_ewallet_app.data.api.ApiService
import com.example.nutech_ewallet_app.data.entity.Response
import org.json.JSONObject

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
        val response = apiService.getBalance(token)
        if (response.isSuccessful) {
            emit(response.body())
        } else {
            val error = JSONObject(response.errorBody()!!.charStream().readText())
            emit(Response(error.getInt("status"), error.getString("message"), null))
        }
    }

    fun getHistoryTransaction(token: String) = liveData {
        val response = apiService.getTransactionHistory(token)
        if (response.isSuccessful) {
            emit(response.body())
        } else {
            val error = JSONObject(response.errorBody()!!.charStream().readText())
            emit(Response(error.getInt("status"), error.getString("message"), null))
        }
    }

    fun topup(token: String, amount: Int) = liveData {
        val response = apiService.topUp(token, amount)
        if (response.isSuccessful) {
            emit(response.body())
        } else {
            val error = JSONObject(response.errorBody()!!.charStream().readText())
            emit(Response(error.getInt("status"), error.getString("message"), null))
        }
    }

    fun transfer(token: String, amount: Int) = liveData {
        val response = apiService.transfer(token, amount)
        if (response.isSuccessful) {
            emit(response.body())
        } else {
            val error = JSONObject(response.errorBody()!!.charStream().readText())
            emit(Response(error.getInt("status"), error.getString("message"), null))
        }
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
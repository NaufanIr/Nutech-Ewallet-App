package com.example.nutech_ewallet_app.data.repository

import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.nutech_ewallet_app.data.SystemPreferences
import com.example.nutech_ewallet_app.data.api.ApiService
import com.example.nutech_ewallet_app.data.entity.Response
import org.json.JSONObject

class UserRepository private constructor(
    private val apiService: ApiService, private val preferences: SystemPreferences
) {

    //TOKEN VIA DATA STORE
    val token = preferences.getToken().asLiveData()

    suspend fun setToken(token: String) {
        preferences.setToken(token)
    }

    //USER DATA FROM API
    fun registration(p0: String, p1: String, p2: String, p3: String) = liveData {
        val response = apiService.registration(p0, p1, p2, p3)
        if (response.isSuccessful) {
            emit(response.body())
        } else {
            val error = JSONObject(response.errorBody()!!.charStream().readText())
            emit(Response(error.getInt("status"), error.getString("message"), null))
        }
    }

    fun login(p0: String, p1: String) = liveData {
        val response = apiService.login(p0, p1)
        if (response.isSuccessful) {
            emit(response.body())
        } else {
            val error = JSONObject(response.errorBody()!!.charStream().readText())
            emit(Response(error.getInt("status"), error.getString("message"), null))
        }
    }

    fun getUserProfile(token: String) = liveData {
        val response = apiService.getProfile(token)
        if (response.isSuccessful) {
            emit(response.body())
        } else {
            val error = JSONObject(response.errorBody()!!.charStream().readText())
            emit(Response(error.getInt("status"), error.getString("message"), null))
        }
    }

    fun updateUserProfile(p0: String, p1: String, p2: String) = liveData {
        val response = apiService.updateProfile(p0, p1, p2)
        if (response.isSuccessful) {
            emit(response.body())
        } else {
            val error = JSONObject(response.errorBody()!!.charStream().readText())
            emit(Response(error.getInt("status"), error.getString("message"), null))
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            preferences: SystemPreferences,
        ): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(apiService, preferences)
        }.also { instance = it }
    }
}
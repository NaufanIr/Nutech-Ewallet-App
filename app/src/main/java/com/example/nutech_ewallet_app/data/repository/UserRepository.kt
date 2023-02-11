package com.example.nutech_ewallet_app.data.repository

import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.nutech_ewallet_app.data.SystemPreferences
import com.example.nutech_ewallet_app.data.api.ApiService

class UserRepository private constructor(
    private val apiService: ApiService,
    private val preferences: SystemPreferences
) {

    //TOKEN VIA DATA STORE
    val token = preferences.getToken().asLiveData()

    suspend fun setToken(token: String) {
        preferences.setToken(token)
    }

    //USER DATA FROM API
    fun registration(p0: String, p1: String, p2: String, p3: String) = liveData {
        emit(apiService.registration(p0, p1, p2, p3))
    }

    fun login(p0: String, p1: String) = liveData {
        emit(apiService.login(p0, p1))
    }

    fun getUserProfile(token: String) = liveData {
        emit(apiService.getProfile(token))
    }

    fun updateUserProfile(p0: String, p1: String, p2: String) = liveData {
        emit(apiService.updateProfile(p0, p1, p2))
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
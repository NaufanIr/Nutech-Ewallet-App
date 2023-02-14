package com.example.nutech_ewallet_app.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutech_ewallet_app.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepo: UserRepository) : ViewModel() {
    val token = userRepo.token

    fun setToken(token: String) {
        viewModelScope.launch {
            userRepo.setToken("Bearer $token")
        }
    }

    fun login(email: String, password: String) = userRepo.login(email, password)
}
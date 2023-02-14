package com.example.nutech_ewallet_app.ui.registration

import androidx.lifecycle.ViewModel
import com.example.nutech_ewallet_app.data.repository.UserRepository

class RegistrationViewModel(private val userRepo: UserRepository) : ViewModel() {
    fun registration(firstName: String, lastName: String, email: String, password: String) =
        userRepo.registration(firstName, lastName, email, password)
}
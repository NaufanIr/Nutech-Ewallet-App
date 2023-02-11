package com.example.nutech_ewallet_app.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutech_ewallet_app.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepo: UserRepository) : ViewModel() {

    private val token = userRepo.token

    fun clearToken() {
        viewModelScope.launch {
            userRepo.setToken("")
        }
    }

    val firstName =
        Transformations.switchMap(token) { userRepo.getUserProfile(it) }.value?.data?.firstName

    val lastName =
        Transformations.switchMap(token) { userRepo.getUserProfile(it) }.value?.data?.lastName

    val email = Transformations.switchMap(token) { userRepo.getUserProfile(it) }.value?.data?.email

    fun updateUserProfile(firstName: String, lastName: String) = Transformations.switchMap(token) {
        userRepo.updateUserProfile(it, firstName, lastName)
    }

    //REMOVE
    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }
    val text: LiveData<String> = _text
}
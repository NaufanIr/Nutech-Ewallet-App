package com.example.nutech_ewallet_app.ui.home

import androidx.lifecycle.*
import com.example.nutech_ewallet_app.data.repository.TransactionRepository
import com.example.nutech_ewallet_app.data.repository.UserRepository

class HomeViewModel(
    private val userRepo: UserRepository,
    private val transactionRepo: TransactionRepository
) : ViewModel() {

    private val token = userRepo.token

    val userName = Transformations.switchMap(token) { token ->
            userRepo.getUserProfile(token)
    }

    val saldoBalance = Transformations.switchMap(token) { transactionRepo.getBalance(it) }

    val transactionHistory = Transformations.switchMap(token) {
        transactionRepo.getHistoryTransaction(it)
    }

    //REMOVE
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}
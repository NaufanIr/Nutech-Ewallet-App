package com.example.nutech_ewallet_app.ui.transfer

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.nutech_ewallet_app.data.repository.TransactionRepository
import com.example.nutech_ewallet_app.data.repository.UserRepository

class TransferViewModel(private val transactionRepo: TransactionRepository): ViewModel() {
    private val token = transactionRepo.token

    val userBalance = Transformations.switchMap(token) {
        transactionRepo.getBalance(it)
    }

    fun transfer(amount: Int) = Transformations.switchMap(token) {
        transactionRepo.transfer(it, amount)
    }
}
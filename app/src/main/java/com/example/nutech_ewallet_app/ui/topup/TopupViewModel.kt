package com.example.nutech_ewallet_app.ui.topup

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.nutech_ewallet_app.data.repository.TransactionRepository

class TopupViewModel(private val transactionRepo: TransactionRepository) : ViewModel() {

    private val token = transactionRepo.token

    fun topup(amount: Int) = Transformations.switchMap(token) {
        transactionRepo.topup(it, amount)
    }
}
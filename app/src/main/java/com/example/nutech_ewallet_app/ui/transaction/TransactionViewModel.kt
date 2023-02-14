package com.example.nutech_ewallet_app.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.nutech_ewallet_app.data.repository.TransactionRepository

class TransactionViewModel(private val transactionRepo: TransactionRepository) : ViewModel() {

    private val token = transactionRepo.token

    val transactions = Transformations.switchMap(token) {
        transactionRepo.getHistoryTransaction(it)
    }
}
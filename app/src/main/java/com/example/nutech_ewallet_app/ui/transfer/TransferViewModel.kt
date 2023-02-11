package com.example.nutech_ewallet_app.ui.transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.nutech_ewallet_app.data.repository.TransactionRepository

class TransferViewModel(private val transactionRepo: TransactionRepository) : ViewModel() {

    private val token = transactionRepo.token

    fun transfer(amount: Int) = Transformations.switchMap(token) {
        transactionRepo.transfer(it, amount)
    }

    //REMOVE
    private val _text = MutableLiveData<String>().apply {
        value = "This is transfer Fragment"
    }
    val text: LiveData<String> = _text
}
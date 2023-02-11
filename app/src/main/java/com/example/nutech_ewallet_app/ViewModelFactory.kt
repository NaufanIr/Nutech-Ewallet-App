package com.example.nutech_ewallet_app

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nutech_ewallet_app.data.Injection
import com.example.nutech_ewallet_app.data.repository.TransactionRepository
import com.example.nutech_ewallet_app.data.repository.UserRepository
import com.example.nutech_ewallet_app.ui.home.HomeViewModel
import com.example.nutech_ewallet_app.ui.profile.ProfileViewModel
import com.example.nutech_ewallet_app.ui.transfer.TransferViewModel

class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(userRepository, transactionRepository) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(TransferViewModel::class.java)) {
            return TransferViewModel(transactionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideUserRepository(context),
                    Injection.provideTransactionRepository(context)
                )
            }.also { instance = it }
    }
}
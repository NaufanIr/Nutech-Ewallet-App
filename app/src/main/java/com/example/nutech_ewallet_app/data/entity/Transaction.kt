package com.example.nutech_ewallet_app.data.entity

import com.google.gson.annotations.SerializedName

data class Transaction(
    @field:SerializedName("transaction_id")
    val transactionId: String?,

    @field:SerializedName("transaction_time")
    val transactionTime: String?,

    @field:SerializedName("transaction_type")
    val transactionType: String?,

    @field:SerializedName("amount")
    val amount: Int?,

    @field:SerializedName("balance")
    val balance: Int?,
)

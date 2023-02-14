package com.example.nutech_ewallet_app.data.entity

import com.google.gson.annotations.SerializedName

data class User(
    @field:SerializedName("token")
    val token: String?,

    @field:SerializedName("first_name")
    val firstName: String?,

    @field:SerializedName("last_name")
    val lastName: String?,

    @field:SerializedName("email")
    val email: String?,

    @field:SerializedName("balance")
    val balance: Int? = 0,
)

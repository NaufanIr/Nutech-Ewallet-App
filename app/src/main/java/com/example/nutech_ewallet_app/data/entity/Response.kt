package com.example.nutech_ewallet_app.data.entity

import com.google.gson.annotations.SerializedName

data class Response<T>(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: T?,
)

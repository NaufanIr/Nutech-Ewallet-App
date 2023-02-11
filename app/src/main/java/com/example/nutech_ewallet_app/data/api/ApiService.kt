package com.example.nutech_ewallet_app.data.api

import com.example.nutech_ewallet_app.data.entity.Response
import com.example.nutech_ewallet_app.data.entity.Transaction
import com.example.nutech_ewallet_app.data.entity.User
import retrofit2.http.*

interface ApiService {
    //USER DOMAIN
    @GET("getProfile")
    suspend fun getProfile(@Header("Authorization") token: String): Response<User?>

    @POST("registration")
    @FormUrlEncoded
    suspend fun registration(
        @Field("email") username: String,
        @Field("password") password: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String
    ): Response<User?>

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") username: String,
        @Field("password") password: String
    ): Response<User?>

    @FormUrlEncoded
    @POST("updateProfile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String
    ): Response<User?>

    //TRANSACTION DOMAIN
    @GET("balance")
    suspend fun getBalance(@Header("Authorization") token: String): Response<Transaction?>

    @GET("transactionHistory")
    suspend fun getTransactionHistory(@Header("Authorization") token: String): Response<Transaction?>

    @FormUrlEncoded
    @POST("transfer")
    suspend fun transfer(
        @Header("Authorization") token: String,
        @Field("amount") amount: Int,
    ): Response<String?>

    @FormUrlEncoded
    @POST("topup")
    suspend fun topUp(
        @Header("Authorization") token: String,
        @Field("amount") amount: Int,
    ): Response<String?>
}
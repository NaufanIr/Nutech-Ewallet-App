package com.example.nutech_ewallet_app.data.api

import com.example.nutech_ewallet_app.data.entity.Response
import com.example.nutech_ewallet_app.data.entity.Transaction
import com.example.nutech_ewallet_app.data.entity.User
import retrofit2.http.*

interface ApiService {
    //USER DOMAIN
    @GET("getProfile")
    suspend fun getProfile(@Header("Authorization") token: String): retrofit2.Response<Response<User?>>

    @GET("balance")
    suspend fun getBalance(@Header("Authorization") token: String): retrofit2.Response<Response<User?>>

    @FormUrlEncoded
    @POST("registration")
    suspend fun registration(
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("email") username: String,
        @Field("password") password: String,
    ): retrofit2.Response<Response<User?>>

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") username: String, @Field("password") password: String
    ): retrofit2.Response<Response<User?>>


    @FormUrlEncoded
    @POST("updateProfile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String
    ): retrofit2.Response<Response<User?>>


    //TRANSACTION DOMAIN
    @GET("transactionHistory")
    suspend fun getTransactionHistory(@Header("Authorization") token: String): retrofit2.Response<Response<List<Transaction?>>>

    @FormUrlEncoded
    @POST("transfer")
    suspend fun transfer(
        @Header("Authorization") token: String,
        @Field("amount") amount: Int,
    ): retrofit2.Response<Response<String?>>

    @FormUrlEncoded
    @POST("topup")
    suspend fun topUp(
        @Header("Authorization") token: String,
        @Field("amount") amount: Int,
    ): retrofit2.Response<Response<String?>>
}
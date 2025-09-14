package com.example.mybank12m.network

import com.example.mybank12m.data.model.Account
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AccountApi {
    @GET("accounts")
    fun fetchAccounts(): Call<List<Account>>

    @POST("accounts")
    fun createAccount(@Body account: Account):Call<Account>

}
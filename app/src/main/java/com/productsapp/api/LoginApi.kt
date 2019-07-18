package com.productsapp.api

import com.google.gson.annotations.SerializedName
import com.productsapp.api.model.AuthResponse
import com.productsapp.api.model.LoginData
import com.productsapp.api.model.Product
import retrofit2.Call
import retrofit2.http.*

interface LoginApi {

    @POST("/api/register/")
    fun signIn(@Body loginData: LoginData) : Call<AuthResponse>

    @POST("/api/register")
    fun signIn2(@Query("username") loginData: String, @Query("password") password: String) : Call<AuthResponse>

    @POST("/api/login/")
    fun login(loginData: LoginData) : Call<AuthResponse>

    @GET("api/products/")
    fun getProducts(@Header("Authorization") token: String?) : Call<List<Product>>
}
package com.productsapp.api

import com.productsapp.api.model.Product
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Header

internal interface ProductsApi {
    @GET("/api/products/")
    fun getProducts(@Header("Authorization") token: String?) : Call<List<Product>>
}

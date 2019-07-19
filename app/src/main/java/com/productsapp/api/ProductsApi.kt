package com.productsapp.api

import com.productsapp.api.model.Comment
import com.productsapp.api.model.CommentResponse
import com.productsapp.api.model.CommentToSend
import com.productsapp.api.model.Product
import retrofit2.Call
import retrofit2.http.*

internal interface ProductsApi {
    @GET("/api/products/")
    fun getProducts(@Header("Authorization") token: String?) : Call<List<Product>>

    @GET("/api/reviews/{id}")
    fun getComments(@Header("Authorization") token: String, @Path("id") id: String) : Call<List<Comment>>

    @POST("/api/reviews/{id}")
    fun sendComment(@Header("Authorization:Token") token: String, @Path("id") productId: String, @Body comment: CommentToSend) : Call<CommentResponse>
}

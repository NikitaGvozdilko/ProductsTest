package com.productsapp.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("success")
    @Expose
    val success: Boolean,
    @SerializedName("token")
    @Expose
    val token: String)
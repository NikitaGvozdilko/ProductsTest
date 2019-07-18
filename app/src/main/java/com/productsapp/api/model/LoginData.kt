package com.productsapp.api.model

import com.google.gson.annotations.SerializedName

data class LoginData(
    val username: String,
    val password: String)
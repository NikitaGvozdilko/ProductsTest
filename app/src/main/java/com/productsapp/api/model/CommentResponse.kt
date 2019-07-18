package com.productsapp.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommentResponse (
    @SerializedName("review_id")
    @Expose
    val reviewId: String
)
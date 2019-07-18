package com.productsapp.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Comment(
        val id: String,

        val product: String,

        @SerializedName("created_by")
        @Expose
        val commentOwner: CommentOwner,

        @SerializedName("created_at")
        @Expose
        val date: String,

        val rate: Float,

        val text: String) {


}
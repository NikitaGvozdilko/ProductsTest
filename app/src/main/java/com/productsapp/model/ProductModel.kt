package com.productsapp.model

import com.productsapp.api.model.Comment
import com.productsapp.api.model.Product

data class ProductModel(
        var id: String,
        var image: String,
        var title: String,
        var text: String,
        var commentsList: List<Comment>,
        var showComments: Boolean = false) {

    fun setData(product: Product){
        id = product.id
        image = product.image
        title = product.title
        text = product.text
    }
}


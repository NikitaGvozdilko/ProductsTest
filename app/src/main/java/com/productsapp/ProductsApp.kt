package com.productsapp

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

class ProductsApp : Application() {
    companion object {
        val networkManager: NetworkManager = NetworkManager()
    }

    override fun onCreate() {
        super.onCreate()
    }


}
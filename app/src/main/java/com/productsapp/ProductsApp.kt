package com.productsapp

import android.app.Application

class ProductsApp : Application() {
    companion object {
        val networkManager: NetworkManager = NetworkManager()
    }

    override fun onCreate() {
        super.onCreate()
    }


}
package com.productsapp

import android.app.Application
import com.productsapp.database.AppDatabase

class ProductsApp : Application() {
    companion object {
        val networkManager: NetworkManager = NetworkManager()
        var appDatabase: AppDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        appDatabase = AppDatabase(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        appDatabase?.close()
    }
}
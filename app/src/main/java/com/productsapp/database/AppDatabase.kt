package com.productsapp.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.productsapp.database.dao.ProfileDao
import com.productsapp.database.model.ProfileModel

@Database(entities = [ProfileModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao() : ProfileDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "AppDatabase.db")
            .allowMainThreadQueries()
            .build()
    }

    fun clearProfile() {
        profileDao().removeProfile()
    }
}
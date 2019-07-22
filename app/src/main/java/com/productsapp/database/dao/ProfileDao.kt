package com.productsapp.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.productsapp.database.model.ProfileModel

@Dao
interface ProfileDao {

    @Insert(onConflict = REPLACE)
    fun saveData(profileModel: ProfileModel)

    @Query("SELECT * FROM Profile limit 1")
    fun getProfile() : ProfileModel

    @Query("DELETE FROM Profile")
    fun removeProfile()

}
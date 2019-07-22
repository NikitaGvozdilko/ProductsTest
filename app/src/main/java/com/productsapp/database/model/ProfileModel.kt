package com.productsapp.database.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

const val TABLE_NAME = "Profile"

@Entity(tableName = TABLE_NAME)
data class ProfileModel(
        var name: String,
        var surname: String,
        var image: String?) {
        @PrimaryKey
        var id: Int = 1
}
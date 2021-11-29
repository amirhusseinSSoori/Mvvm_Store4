package com.example.myapplication.data.db

import androidx.room.TypeConverter
import com.example.myapplication.data.db.entity.OwnerEntity
import com.google.gson.Gson


import java.io.Serializable

class Converter : Serializable {
    @TypeConverter
    fun ownerToString(app: OwnerEntity): String = Gson().toJson(app)

    @TypeConverter
    fun stringToOwner(string: String): OwnerEntity = Gson().fromJson(string, OwnerEntity::class.java)
}


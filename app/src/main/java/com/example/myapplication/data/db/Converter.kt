package com.example.myapplication.data.db

import androidx.room.TypeConverter
import com.example.myapplication.data.db.enity.OwnerEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


import java.io.Serializable

class Converter : Serializable {
    @TypeConverter
    fun appToString(app: OwnerEntity): String = Gson().toJson(app)

    @TypeConverter
    fun stringToApp(string: String): OwnerEntity = Gson().fromJson(string, OwnerEntity::class.java)
}


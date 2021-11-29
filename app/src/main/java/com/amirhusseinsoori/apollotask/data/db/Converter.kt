package com.amirhusseinsoori.apollotask.data.db

import androidx.room.TypeConverter
import com.amirhusseinsoori.apollotask.data.db.entity.OwnerEntity
import com.google.gson.Gson


import java.io.Serializable

class Converter : Serializable {
    @TypeConverter
    fun ownerToString(app: OwnerEntity): String = Gson().toJson(app)

    @TypeConverter
    fun stringToOwner(string: String): OwnerEntity = Gson().fromJson(string, OwnerEntity::class.java)
}


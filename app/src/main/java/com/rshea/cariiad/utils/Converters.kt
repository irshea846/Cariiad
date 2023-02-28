package com.rshea.cariiad.utils

import androidx.room.TypeConverter
import com.google.gson.Gson


object Converters {

    @TypeConverter
    fun fromList(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(s: String?): List<String> {
        @Suppress("UNCHECKED_CAST")
        return Gson().fromJson(s, ArrayList::class.java).toList() as List<String>
    }
}
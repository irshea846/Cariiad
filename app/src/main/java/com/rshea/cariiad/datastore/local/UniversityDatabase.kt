package com.rshea.cariiad.datastore.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UniversityCacheEntity::class], version = 1)
abstract class UniversityDatabase: RoomDatabase() {

    abstract fun uniDao(): UniversityDao

    companion object {
        const val DATABASE_NAME: String = "uni_data_table"
    }
}
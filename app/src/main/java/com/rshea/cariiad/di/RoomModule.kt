package com.rshea.cariiad.di

import android.content.Context
import androidx.room.Room
import com.rshea.cariiad.datastore.local.UniversityDao
import com.rshea.cariiad.datastore.local.UniversityDatabase

object RoomModule {
    fun provideUniDB(context: Context): UniversityDatabase {
        return Room
            .databaseBuilder(
                context,
                UniversityDatabase::class.java,
                UniversityDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideUniversityDao(universityDatabase: UniversityDatabase): UniversityDao {
        return universityDatabase.uniDao()
    }
}
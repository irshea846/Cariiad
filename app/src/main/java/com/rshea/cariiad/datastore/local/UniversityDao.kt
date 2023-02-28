package com.rshea.cariiad.datastore.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UniversityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(UniEntity: UniversityCacheEntity): Long

    @Query("SELECT * FROM uni_data_table")
    suspend fun get(): List<UniversityCacheEntity>
}
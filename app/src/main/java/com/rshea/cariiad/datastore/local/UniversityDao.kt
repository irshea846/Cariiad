package com.rshea.cariiad.datastore.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UniversityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(uniEntity: UniversityCacheEntity): Long

    @Query("SELECT * FROM uni_data_table")
    suspend fun get(): List<UniversityCacheEntity>

    @Query("SELECT * FROM uni_data_table WHERE id = :id")
    suspend fun get(id: Int): UniversityCacheEntity
}
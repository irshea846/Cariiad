package com.rshea.cariiad.datastore.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "uni_data_table")
data class UniversityCacheEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "uni_name")
    var name: String,

    @ColumnInfo(name = "uni_country")
    var country: String,

    @ColumnInfo(name = "uni_webpages")
    var webPages: String,

    )

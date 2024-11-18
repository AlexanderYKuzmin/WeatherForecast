package com.kuzmin.weatherforecast.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clouds")
data class CloudsDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "clouds_id")
    val id: Int,

    val all: Int,

    @ColumnInfo(name = "clouds_date_id")
    val dateId: Long
)

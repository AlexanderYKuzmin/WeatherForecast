package com.kuzmin.weatherforecast.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "date")
data class DateDb (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "date_id")
    val id: Long,

    val date: Long,

    @ColumnInfo(name = "date_text")
    val dateText: String,

    @ColumnInfo(name = "date_city_id")
    val cityId: Int
)

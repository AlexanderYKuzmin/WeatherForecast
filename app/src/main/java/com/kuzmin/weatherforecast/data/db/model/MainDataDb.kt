package com.kuzmin.weatherforecast.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "main_data")
data class MainDataDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "main_data_id")
    val id: Int,

    val temp: Double,

    @ColumnInfo(name = "feels_like")
    val feelsLike: Double,

    @ColumnInfo(name = "temp_min")
    val tempMin: Double,

    @ColumnInfo(name = "temp_max")
    val tempMax: Double,

    val pressure: Int,

    val humidity: Int,

    @ColumnInfo(name = "main_data_date_id")
    val dateId: Long
)

package com.kuzmin.weatherforecast.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "weather_id")
    val id: Int,

    val main: String,

    val description: String,

    val icon: String,

    @ColumnInfo(name = "weather_date_id")
    val dateId: Long
)

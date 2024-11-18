package com.kuzmin.weatherforecast.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coord")
data class CoordDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "coord_id")
    val id: Int,

    val lat: Double,

    val lon: Double,

    @ColumnInfo(name = "coord_city_id")
    val cityId: Int
)

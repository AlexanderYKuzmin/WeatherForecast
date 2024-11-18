package com.kuzmin.weatherforecast.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wind")
data class WindDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "wind_id")
    val id: Int,

    val speed: Double,

    val deg: Int,

    @ColumnInfo(name = "wind_date_id")
    val dateId: Long
)

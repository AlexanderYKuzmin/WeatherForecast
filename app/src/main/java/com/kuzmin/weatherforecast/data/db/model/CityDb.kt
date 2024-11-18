package com.kuzmin.weatherforecast.data.db.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "city",
    indices = [
        Index(
            value = ["name"], unique = true
        )
    ]
)
data class CityDb(
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val name: String,

    val country: String,

    val sunrise: Int,

    val sunset: Int
)

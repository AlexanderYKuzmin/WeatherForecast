package com.kuzmin.weatherforecast.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sys")
data class SysDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sys_id")
    val id: Int,

    val pod: String,

    @ColumnInfo(name = "sys_date_id")
    val dateId: Long
)

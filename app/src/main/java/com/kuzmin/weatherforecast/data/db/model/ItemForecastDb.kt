package com.kuzmin.weatherforecast.data.db.model

import androidx.room.Embedded
import androidx.room.Relation

data class ItemForecastDb(
    @Embedded
    val dateDb: DateDb,

    @Relation(
        entity = WeatherDb::class,
        parentColumn = "date_id",
        entityColumn = "weather_date_id"
    )
    val weatherDb: WeatherDb,

    @Relation(
        entity = MainDataDb::class,
        parentColumn = "date_id",
        entityColumn = "main_data_date_id"
    )
    val mainDataDb: MainDataDb,

    @Relation(
        entity = WindDb::class,
        parentColumn = "date_id",
        entityColumn = "wind_date_id"
    )
    val windDb: WindDb,

    @Relation(
        entity = SysDb::class,
        parentColumn = "date_id",
        entityColumn = "sys_date_id"
    )
    val sysDb: SysDb,

    @Relation(
        entity = CloudsDb::class,
        parentColumn = "date_id",
        entityColumn = "clouds_date_id"
    )
    val cloudsDb: CloudsDb
)

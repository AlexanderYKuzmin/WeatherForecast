package com.kuzmin.weatherforecast.data.db.model

import androidx.room.Embedded
import androidx.room.Relation

data class ForecastDb (
    @Embedded
    val cityDb: CityDb,

    @Relation(
        entity = CoordDb::class,
        parentColumn = "id",
        entityColumn = "coord_city_id"
    )
    val coordDb: CoordDb,

    @Relation(
        entity = DateDb::class,
        parentColumn = "id",
        entityColumn = "date_city_id"
    )
    val listDb: List<ItemForecastDb>
)
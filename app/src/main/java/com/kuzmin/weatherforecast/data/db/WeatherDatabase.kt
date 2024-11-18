package com.kuzmin.weatherforecast.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kuzmin.weatherforecast.data.db.model.CityDb
import com.kuzmin.weatherforecast.data.db.model.CloudsDb
import com.kuzmin.weatherforecast.data.db.model.CoordDb
import com.kuzmin.weatherforecast.data.db.model.DateDb
import com.kuzmin.weatherforecast.data.db.model.MainDataDb
import com.kuzmin.weatherforecast.data.db.model.SysDb
import com.kuzmin.weatherforecast.data.db.model.WeatherDb
import com.kuzmin.weatherforecast.data.db.model.WindDb

@Database(
    entities = [
        CityDb::class,
        DateDb::class,
        CloudsDb::class,
        CoordDb::class,
        MainDataDb::class,
        SysDb::class,
        WeatherDb::class,
        WindDb::class
    ],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
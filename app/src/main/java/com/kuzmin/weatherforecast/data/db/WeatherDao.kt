package com.kuzmin.weatherforecast.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.kuzmin.weatherforecast.data.db.model.CityDb
import com.kuzmin.weatherforecast.data.db.model.CloudsDb
import com.kuzmin.weatherforecast.data.db.model.CoordDb
import com.kuzmin.weatherforecast.data.db.model.DateDb
import com.kuzmin.weatherforecast.data.db.model.ForecastDb
import com.kuzmin.weatherforecast.data.db.model.ItemForecastDb
import com.kuzmin.weatherforecast.data.db.model.MainDataDb
import com.kuzmin.weatherforecast.data.db.model.SysDb
import com.kuzmin.weatherforecast.data.db.model.WeatherDb
import com.kuzmin.weatherforecast.data.db.model.WindDb
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert
    suspend fun addCity(cityDb: CityDb)

    @Insert
    suspend fun addCoord(coordDb: CoordDb)

    @Insert
    suspend fun addDate(dateDb: DateDb)

    @Insert
    suspend fun addWeather(weatherDb: WeatherDb)

    @Insert
    suspend fun addMainData(mainDataDb: MainDataDb)

    @Insert
    suspend fun addWind(windDb: WindDb)

    @Insert
    suspend fun addSys(sysDb: SysDb)

    @Insert
    suspend fun addClouds(cloudsDb: CloudsDb)

    suspend fun addList(listDb: List<ItemForecastDb>) {
        listDb.forEach {
            addDate(it.dateDb)
            addWeather(it.weatherDb)
            addMainData(it.mainDataDb)
            addWind(it.windDb)
            addSys(it.sysDb)
            addClouds(it.cloudsDb)
        }
    }

    @Transaction
    suspend fun insertForecastData(forecastDb: ForecastDb) {
        addCity(forecastDb.cityDb)
        addCoord(forecastDb.coordDb)
        addList(forecastDb.listDb)
    }

    @Query("SELECT *, * FROM city " +
            "JOIN coord ON city.id = coord.coord_city_id")
    fun getForecast(): Flow<ForecastDb?>
}
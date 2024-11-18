package com.kuzmin.weatherforecast.data.mappers

import com.kuzmin.weatherforecast.data.db.model.CityDb
import com.kuzmin.weatherforecast.data.db.model.CloudsDb
import com.kuzmin.weatherforecast.data.db.model.CoordDb
import com.kuzmin.weatherforecast.data.db.model.ForecastDb
import com.kuzmin.weatherforecast.data.db.model.ItemForecastDb
import com.kuzmin.weatherforecast.data.db.model.MainDataDb
import com.kuzmin.weatherforecast.data.db.model.SysDb
import com.kuzmin.weatherforecast.data.db.model.WeatherDb
import com.kuzmin.weatherforecast.data.db.model.WindDb
import com.kuzmin.weatherforecast.domain.model.forecast.Coord
import com.kuzmin.weatherforecast.domain.model.forecast.City
import com.kuzmin.weatherforecast.domain.model.forecast.Clouds
import com.kuzmin.weatherforecast.domain.model.forecast.Forecast
import com.kuzmin.weatherforecast.domain.model.forecast.ItemForecast
import com.kuzmin.weatherforecast.domain.model.forecast.MainData
import com.kuzmin.weatherforecast.domain.model.forecast.Sys
import com.kuzmin.weatherforecast.domain.model.forecast.Weather
import com.kuzmin.weatherforecast.domain.model.forecast.Wind
import com.kuzmin.weatherforecast.extensions.toLocalDateTimeFromSeconds
import javax.inject.Inject

class DbToModelMapper @Inject constructor() {

    fun mapForecastDbToForecast(forecastDb: ForecastDb): Forecast {
        return Forecast(
            city = mapCityDbToCity(forecastDb.cityDb),
            coord = mapCoordDbToCoord(forecastDb.coordDb),
            list = mapItemForecastDbListToItemForecastList(forecastDb.listDb)
        )
    }

    private fun mapCityDbToCity(cityDb: CityDb): City {
        with(cityDb) {
            return City(
                name = name,
                country = country,
                sunrise = sunrise,
                sunset = sunset
            )
        }
    }

    private fun mapCoordDbToCoord(coordDb: CoordDb): Coord {
        with(coordDb) {
            return Coord(lat, lon)
        }
    }

    private fun mapItemForecastDbListToItemForecastList(listDb: List<ItemForecastDb>): List<ItemForecast> {
        return listDb.map { mapItemForecastDbToItemForecast(it) }
    }

    private fun mapItemForecastDbToItemForecast(itemForecastDb: ItemForecastDb): ItemForecast {
        with(itemForecastDb) {
            return ItemForecast(
                date = dateDb.date.toLocalDateTimeFromSeconds(),
                dateText = dateDb.dateText,
                weather = mapWeatherDbToWeather(weatherDb),
                mainData = mapMainDataDbToMainData(mainDataDb),
                wind = mapWindDbToWind(windDb),
                sys = mapSysDbToSys(sysDb),
                clouds = mapCloudsDbToClouds(cloudsDb)
            )
        }
    }

    private fun mapWeatherDbToWeather(weatherDb: WeatherDb): Weather {
        with(weatherDb) {
            return Weather(
                main = main,
                description = description,
                icon = icon
            )
        }
    }

    private fun mapMainDataDbToMainData(mainDataDb: MainDataDb): MainData {
        with(mainDataDb) {
            return MainData(
                temp = temp,
                feelsLike = feelsLike,
                tempMin = tempMin,
                tempMax = tempMax,
                pressure = pressure,
                humidity = humidity
            )
        }
    }

    private fun mapWindDbToWind(windDb: WindDb): Wind {
        with(windDb) {
            return Wind(
                speed = speed,
                deg = deg
            )
        }
    }

    private fun mapSysDbToSys(sysDb: SysDb): Sys {
        with(sysDb) {
            return Sys(
                pod = pod
            )
        }
    }

    private fun mapCloudsDbToClouds(cloudsDb: CloudsDb): Clouds {
        with(cloudsDb) {
            return Clouds(
                all = all
            )
        }
    }
}
package com.kuzmin.weatherforecast.data.mappers

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
import com.kuzmin.weatherforecast.data.network.model.CityDto
import com.kuzmin.weatherforecast.data.network.model.CloudsDto
import com.kuzmin.weatherforecast.data.network.model.CoordDto
import com.kuzmin.weatherforecast.data.network.model.ForecastDataDto
import com.kuzmin.weatherforecast.data.network.model.ForecastJsonContainer
import com.kuzmin.weatherforecast.data.network.model.MainDto
import com.kuzmin.weatherforecast.data.network.model.SysDto
import com.kuzmin.weatherforecast.data.network.model.WeatherDto
import com.kuzmin.weatherforecast.data.network.model.WindDto
import java.util.Date
import javax.inject.Inject

class DtoToDbModelMapper @Inject constructor() {
    fun mapForecastDataDtoToForecastDb(forecastJsonContainer: ForecastJsonContainer): ForecastDb {
        if (forecastJsonContainer.cnt == 0) return throw RuntimeException("Count of days is 0")

        return ForecastDb(
            cityDb = mapCityDtoToCityDb(forecastJsonContainer.city),
            coordDb = mapCoordDtoToCoordDb(
                forecastJsonContainer.city.coord,
                forecastJsonContainer.city.id
            ),
            listDb = mapForecastDataDtoListToItemForecastDbList(
                forecastJsonContainer.list, forecastJsonContainer.city.id
            )
        )
    }

    private fun mapCityDtoToCityDb(cityDto: CityDto): CityDb {
        with(cityDto) {
            return CityDb(
                id = id,
                name = name,
                country = country,
                sunrise = sunrise,
                sunset = sunset
            )
        }
    }

    private fun mapCoordDtoToCoordDb(coordDto: CoordDto, cityId: Int): CoordDb {
        with(coordDto) {
            return CoordDb(
                id = 0,
                lat = lat,
                lon = lon,
                cityId = cityId
            )
        }
    }

    private fun mapForecastDataDtoListToItemForecastDbList(
        forecastDataDtoList: List<ForecastDataDto>,
        cityId: Int
    ): List<ItemForecastDb> {
        if (forecastDataDtoList.isNullOrEmpty()) return throw RuntimeException("Count of days is 0")
        return forecastDataDtoList.map { mapForecastDataDtoToItemForecastDb(it, cityId) }
    }

    private fun mapForecastDataDtoToItemForecastDb(
        forecastDataDto: ForecastDataDto,
        cityId: Int
    ): ItemForecastDb {
        with(forecastDataDto) {
            return ItemForecastDb(
                dateDb = mapForecastDtoToDateDb(forecastDataDto, cityId),
                weatherDb = mapWeatherDtoToWeatherDb(weather.first(), forecastDataDto.dt),
                mainDataDb = mapMainDataDtoToMainDataDb(forecastDataDto.main, forecastDataDto.dt),
                windDb = mapWindDtoToWindDb(forecastDataDto.wind, forecastDataDto.dt),
                sysDb = mapSysDtoToSysDb(forecastDataDto.sys, forecastDataDto.dt),
                cloudsDb = mapCloudsDtoToCloudsDb(forecastDataDto.clouds, forecastDataDto.dt)
            )
        }
    }

    private fun mapForecastDtoToDateDb(forecastDataDto: ForecastDataDto, cityId: Int): DateDb {
        with(forecastDataDto) {
            return DateDb(
                id = dt,
                dateText = dtTxt,
                date = dt,
                cityId = cityId
            )
        }
    }

    private fun mapWeatherDtoToWeatherDb(weatherDto: WeatherDto, dateDbId: Long): WeatherDb {
        with(weatherDto) {
            return WeatherDb(
                id = 0,
                main = main,
                description = description,
                icon = icon,
                dateId = dateDbId
            )
        }
    }

    private fun mapMainDataDtoToMainDataDb(mainDto: MainDto, dateDbId: Long): MainDataDb {
        with(mainDto) {
            return MainDataDb(
                id = 0,
                temp = mainDto.temp,
                feelsLike = mainDto.feelsLike,
                tempMin = mainDto.tempMin,
                tempMax = mainDto.tempMax,
                pressure = mainDto.pressure,
                humidity = mainDto.humidity,
                dateId = dateDbId
            )
        }
    }

    private fun mapWindDtoToWindDb(windDto: WindDto, dateDbId: Long): WindDb {
        with(windDto) {
            return WindDb(
                id = 0,
                speed = speed,
                deg = deg,
                dateId = dateDbId
            )
        }
    }

    private fun mapSysDtoToSysDb(sysDto: SysDto, dateDbId: Long): SysDb {
        with(sysDto) {
            return SysDb(
                id = 0,
                pod = pod,
                dateId = dateDbId
            )
        }
    }

    private fun mapCloudsDtoToCloudsDb(cloudsDto: CloudsDto, dateDbId: Long): CloudsDb {
        with(cloudsDto) {
            return CloudsDb(
                id = 0,
                all = all,
                dateId = dateDbId
            )
        }
    }
}
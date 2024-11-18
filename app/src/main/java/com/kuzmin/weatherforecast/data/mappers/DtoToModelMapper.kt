package com.kuzmin.weatherforecast.data.mappers

import com.kuzmin.weatherforecast.data.network.model.CoordDto
import com.kuzmin.weatherforecast.domain.model.forecast.Coord
import javax.inject.Inject

class DtoToModelMapper @Inject constructor() {
    fun mapCoordDtoToCoord(coordDto: CoordDto): Coord {
        return Coord(
            coordDto.lat,
            coordDto.lon
        )
    }
}
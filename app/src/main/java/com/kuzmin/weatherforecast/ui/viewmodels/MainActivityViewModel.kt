package com.kuzmin.weatherforecast.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.weatherforecast.domain.model.forecast.Coord
import com.kuzmin.weatherforecast.domain.model.servicable.ForecastOperationResult
import com.kuzmin.weatherforecast.domain.usecases.LoadDataFromStorageUseCase
import com.kuzmin.weatherforecast.domain.usecases.StoreLocationDataUseCase
import com.kuzmin.weatherforecast.domain.usecases.db.GetCityNameFromDbUseCase
import com.kuzmin.weatherforecast.domain.usecases.network.GetForecastNetworkByLocationUseCase
import com.kuzmin.weatherforecast.domain.usecases.network.GetLocationByCityNameUseCase
import com.kuzmin.weatherforecast.util.AppConstants
import com.kuzmin.weatherforecast.util.exceptions.LocationDataException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val storeLocationDataUseCase: StoreLocationDataUseCase,
    private val loadDataFromStorageUseCase: LoadDataFromStorageUseCase,
    private val getForecastNetworkByLocationUseCase: GetForecastNetworkByLocationUseCase,
    private val getLocationByCityNameUseCase: GetLocationByCityNameUseCase,
    private val getCityNameFromDbUseCase: GetCityNameFromDbUseCase,
) : ViewModel(){

    private val _appState = MutableLiveData(AppState())
    val appState: LiveData<AppState> get() = _appState

    private val _forecastOperationResult = MutableLiveData<ForecastOperationResult>()
    val forecastOperationResult: LiveData<ForecastOperationResult> get() = _forecastOperationResult

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _forecastOperationResult.postValue(ForecastOperationResult.Error(throwable))
    }

    fun storeLocationData(locationData: Coord) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            storeLocationDataUseCase.storeLocationData(locationData)

            _forecastOperationResult.postValue(ForecastOperationResult.LocationSavedDatastoreSuccess(locationData))
        }
    }

    fun loadDataFromStorage() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val cityAndLocation = loadDataFromStorageUseCase.loadCityAndLocation()

            if (cityAndLocation.location == Coord(0.0, 0.0)) {
                throw LocationDataException()
            }
            _forecastOperationResult.postValue(
                ForecastOperationResult.GetForecastDataFromDatastoreSuccess(cityAndLocation.location)
            )
            _appState.postValue(
                _appState.value?.copy(title = cityAndLocation.cityName)
            )
        }
    }

    fun loadDataFromNetwork(locationData: Coord) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _forecastOperationResult.postValue(ForecastOperationResult.Loading)

            getForecastNetworkByLocationUseCase.getForecastByLocation(locationData)

            _forecastOperationResult.postValue(
                ForecastOperationResult.GetForecastDataFromNetworkSuccess
            )
        }
    }

    private fun getLocationByCityName(cityName: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _forecastOperationResult.postValue(ForecastOperationResult.Loading)

            val locationData = getLocationByCityNameUseCase.getLocationByCityName(cityName)

            _forecastOperationResult.postValue(
                ForecastOperationResult.GetLocationByCityNameFromNetworkSuccess(locationData)
            )
        }
    }

    fun handleSearchQuery(query: String?) {
        val editedQuery = query?.trim() ?: ""
        getLocationByCityName(editedQuery)
        _appState.value = _appState.value?.copy(title = editedQuery)
    }

    fun updateAppState(cityName: String? = null) {
        if (cityName == null) {
            viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                val title = getCityNameFromDbUseCase.getCityName()

                _appState.postValue(
                    _appState.value?.copy(title = title)
                )
            }
        } else {
            _appState.value = _appState.value?.copy(title = cityName)
        }
    }

    data class AppState(
        val title: String = AppConstants.APP_NAME
    )
}
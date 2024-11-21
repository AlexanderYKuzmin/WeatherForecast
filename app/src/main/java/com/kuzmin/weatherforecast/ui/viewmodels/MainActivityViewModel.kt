package com.kuzmin.weatherforecast.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.weatherforecast.domain.CryptoManager
import com.kuzmin.weatherforecast.domain.model.forecast.Coord
import com.kuzmin.weatherforecast.domain.model.servicable.ForecastOperationResult
import com.kuzmin.weatherforecast.domain.usecases.LoadDataFromStorageUseCase
import com.kuzmin.weatherforecast.domain.usecases.StoreLocationDataUseCase
import com.kuzmin.weatherforecast.domain.usecases.network.GetForecastNetworkByLocationUseCase
import com.kuzmin.weatherforecast.domain.usecases.network.GetLocationByCityNameUseCase
import com.kuzmin.weatherforecast.domain.usecases.network.GetLocationByCityNameUseCaseImpl
import com.kuzmin.weatherforecast.util.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.charset.Charset
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val storeLocationDataUseCase: StoreLocationDataUseCase,
    private val loadDataFromStorageUseCaseImpl: LoadDataFromStorageUseCase,
    private val getForecastNetworkByLocationUseCase: GetForecastNetworkByLocationUseCase,
    private val getLocationByCityNameUseCase: GetLocationByCityNameUseCase,
    val cryptoManager: CryptoManager
) : ViewModel(){

    private val _appState = MutableLiveData(AppState())
    val appState: LiveData<AppState> get() = _appState

    private val _forecastOperationResult = MutableLiveData<ForecastOperationResult>()
    val forecastOperationResult: LiveData<ForecastOperationResult> get() = _forecastOperationResult

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _forecastOperationResult.postValue(ForecastOperationResult.Error(throwable))
    }

    fun storeLocationData(locationData: Coord, city: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            storeLocationDataUseCase.storeLocationData(locationData, city)
        }
    }

    fun loadDataFromStorage() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _appState.postValue(
                _appState.value?.copy(title = loadDataFromStorageUseCaseImpl.loadCity())
            )
        }
    }

    fun loadDataFromNetwork(locationData: Coord) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _forecastOperationResult.postValue(ForecastOperationResult.Loading)

            getForecastNetworkByLocationUseCase.getForecastByLocation(locationData)

            _forecastOperationResult.postValue(
                ForecastOperationResult.GetForecastDataFromServerSuccess
            )
        }
    }

    fun getLocationByCityName(cityName: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _forecastOperationResult.postValue(ForecastOperationResult.Loading)

            val locationData = getLocationByCityNameUseCase.getLocationByCityName(cityName)

            _forecastOperationResult.postValue(
                ForecastOperationResult.GetLocationSuccess(locationData, cityName)
            )
        }
    }

    fun handleSearchQuery(query: String?) {
        val editedQuery = query?.trim() ?: ""
        _appState.value = _appState.value?.copy(title = editedQuery)
    }

    data class AppState(
        val title: String = AppConstants.APP_NAME,
    )
}
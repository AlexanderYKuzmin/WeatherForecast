package com.kuzmin.weatherforecast.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.weatherforecast.domain.CryptoManager
import com.kuzmin.weatherforecast.domain.PrefManager
import com.kuzmin.weatherforecast.domain.model.servicable.ApiKeyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(
    private val cryptoManager: CryptoManager,
    private val prefManager: PrefManager
) : ViewModel() {

    private val _apiKeyResult = MutableLiveData<ApiKeyResult>()
    val apiKeyResult: LiveData<ApiKeyResult> get() = _apiKeyResult

    private val settingsCoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _apiKeyResult.postValue(ApiKeyResult.Error(throwable))
    }

    fun getApiKey() {
        viewModelScope.launch(Dispatchers.IO) {
            _apiKeyResult.postValue(
                ApiKeyResult.Success(
                    prefManager.readApiKey()
                )
            )
        }
    }

    fun saveApiKey(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            prefManager.storeApiKey(apiKey)
        }
    }
}
package com.kuzmin.weatherforecast.domain.model.servicable

sealed class ApiKeyResult {

    class Success(val apiKey: String) : ApiKeyResult()

    data class Error(val throwable: Throwable) : ApiKeyResult()
}
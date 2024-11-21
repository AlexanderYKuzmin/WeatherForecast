package com.kuzmin.weatherforecast.data.network

import com.kuzmin.weatherforecast.data.network.ApiService.Companion.APIID
import com.kuzmin.weatherforecast.data.network.ApiService.Companion.LANGUAGE
import com.kuzmin.weatherforecast.data.network.ApiService.Companion.UNITS
import com.kuzmin.weatherforecast.util.AppConstants.DEFAULT_API_KEY
import com.kuzmin.weatherforecast.util.AppConstants.METRIC_SYSTEM
import com.kuzmin.weatherforecast.util.AppConstants.RU
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RequestInterceptor @Inject constructor(
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .addHeader("Content-Type", "application/json")
            .url(
                original.url().newBuilder()
                    .addQueryParameter(UNITS, METRIC_SYSTEM)
                    .addQueryParameter(LANGUAGE, RU)
                    .build()
            )
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
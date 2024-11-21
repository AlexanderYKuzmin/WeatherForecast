package com.kuzmin.weatherforecast.extensions

import android.Manifest
import android.app.Activity
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.kuzmin.weatherforecast.R
import com.kuzmin.weatherforecast.R.string.alert_dialog_permission_message
import com.kuzmin.weatherforecast.R.string.alert_dialog_permission_title
fun Activity.getNetworkRequest() = NetworkRequest.Builder()
    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
    .build()



fun Activity.requestLocationPermission() {
    runOnUiThread {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(alert_dialog_permission_title))
            .setMessage(resources.getString(alert_dialog_permission_message))
            .setCancelable(false)
            .setPositiveButton("Ok") { _, _ ->
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    RUNTIME_PERMISSION_REQUEST_CODE
                )
            }
            .show()
    }
}

fun Activity.showExceptionMessage(message: String, throwable: Throwable) {
    runOnUiThread {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("Ok") { _, _ ->
                Log.d("EXCEPTION", throwable.message.toString())}
            .show()
    }
}

fun Activity.showAlert(message: String) {
    runOnUiThread {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("Ok") { _, _ -> }
            .show()
    }
}

fun Activity.checkNetworkConnection(connectivityManager: ConnectivityManager): Boolean {
    val activeNetwork: Network? = connectivityManager.activeNetwork
    if ( activeNetwork != null) {
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        if (networkCapabilities != null) {
            return if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                Log.d("NetworkCapability", "Network CAPABILITY_INTERNET")
                true
            } else {
               showAlert(getString(R.string.restricted_internet_connection))
                false
            }
        }
    } else {
        showAlert(getString(R.string.failed_internet_connection))
    }
    return false
}

private const val RUNTIME_PERMISSION_REQUEST_CODE = 2
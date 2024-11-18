package com.kuzmin.weatherforecast.extensions

import android.Manifest
import android.app.Activity
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.kuzmin.weatherforecast.R

fun Activity.requestLocationPermission() {
    runOnUiThread {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.alert_dialog_permission_title))
            .setMessage("Starting from Android 6, the system requires apps to be granted ")
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

private const val RUNTIME_PERMISSION_REQUEST_CODE = 2
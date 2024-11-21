package com.kuzmin.weatherforecast.domain

import java.io.InputStream
import java.io.OutputStream

interface CryptoManager {

    fun encrypt(data: ByteArray): ByteArray

    fun decrypt(data: ByteArray): ByteArray

}
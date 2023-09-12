package com.awscherb.cardkeeper.barcode.db

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.zxing.BarcodeFormat

object BarcodeConverters  {

    private val gson by lazy { GsonBuilder().create() }
    @[TypeConverter JvmStatic]
    fun fromString(string: String): BarcodeFormat = BarcodeFormat.valueOf(string)

    @[TypeConverter JvmStatic]
    fun toString(format: BarcodeFormat): String = format.toString()

}
package com.awscherb.cardkeeper.util.db

import androidx.room.TypeConverter
import com.awscherb.cardkeeper.data.entity.BarcodeStruct
import com.awscherb.cardkeeper.data.entity.BoardingPassStruct
import com.awscherb.cardkeeper.data.model.BoardingPass
import com.awscherb.cardkeeper.data.model.FieldObject
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.zxing.BarcodeFormat

object TypeConverters {

    private val gson by lazy { GsonBuilder().create() }

    @[TypeConverter JvmStatic]
    fun fromString(string: String): BarcodeFormat = BarcodeFormat.valueOf(string)

    @[TypeConverter JvmStatic]
    fun toString(format: BarcodeFormat): String = format.toString()

    @[TypeConverter JvmStatic]
    fun toBarcodeList(list: String?): List<BarcodeStruct>? {
        val listType = object : TypeToken<List<BarcodeStruct>?>() {
        }.type
        return gson.fromJson(list, listType)
    }

    @[TypeConverter JvmStatic]
    fun fromBarcodeList(list: List<BarcodeStruct>?): String? {
        return gson.toJson(list)
    }

    @[TypeConverter JvmStatic]
    fun toBoardingPass(pass: String?): BoardingPassStruct? {
        return gson.fromJson(pass, BoardingPassStruct::class.java)
    }

    @[TypeConverter JvmStatic]
    fun fromBoardingPass(list: BoardingPassStruct?): String? {
        return gson.toJson(list)
    }


}
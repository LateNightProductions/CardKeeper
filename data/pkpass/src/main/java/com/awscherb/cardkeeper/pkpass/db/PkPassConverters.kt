package com.awscherb.cardkeeper.pkpass.db

import androidx.room.TypeConverter
import com.awscherb.cardkeeper.pkpass.entity.BarcodeStruct
import com.awscherb.cardkeeper.pkpass.entity.PassInfoStruct
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object PkPassConverters {

    private val gson by lazy { GsonBuilder().create() }

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
    fun toPassInfo(pass: String?): PassInfoStruct? {
        return gson.fromJson(pass, PassInfoStruct::class.java)
    }

    @[TypeConverter JvmStatic]
    fun fromPassInfo(list: PassInfoStruct?): String? {
        return gson.toJson(list)
    }
}
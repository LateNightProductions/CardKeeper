package com.awscherb.cardkeeper.barcode.db

import androidx.room.TypeConverter
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.result.ParsedResultType

object BarcodeConverters {

    @[TypeConverter JvmStatic]
    fun fromString(string: String): BarcodeFormat = BarcodeFormat.valueOf(string)

    @[TypeConverter JvmStatic]
    fun toString(format: BarcodeFormat): String = format.toString()

    @[TypeConverter JvmStatic]
    fun fromParsedType(parsedResultType: ParsedResultType): Int = parsedResultType.ordinal

    @[TypeConverter JvmStatic]
    fun toParsedType(ordinal: Int): ParsedResultType = ParsedResultType.entries[ordinal]
}
package com.awscherb.cardkeeper.barcode.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.awscherb.cardkeeper.barcode.entity.ScannedCodeEntity

@Database(
    entities = [
        ScannedCodeEntity::class,
    ],
    version = 13, exportSchema = false
)
@TypeConverters(
    com.awscherb.cardkeeper.common.TypeConverters::class,
    BarcodeConverters::class
)
abstract class BarcodeDatabase : RoomDatabase() {
    abstract fun scannedCodeDao(): ScannedCodeDao
}
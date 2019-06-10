package com.awscherb.cardkeeper.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.awscherb.cardkeeper.data.dao.ScannedCodeDao
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.util.db.BarcodeConverters

@Database(entities = [(ScannedCode::class)], version = 11, exportSchema = false)
@TypeConverters(BarcodeConverters::class)
abstract class CardKeeperDatabase : RoomDatabase() {
    abstract fun scannedCodeDao(): ScannedCodeDao
}
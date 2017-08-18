package com.awscherb.cardkeeper.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.awscherb.cardkeeper.data.dao.ScannedCodeDao
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.util.db.BarcodeConverters

@Database(entities = arrayOf(ScannedCode::class), version = 10)
@TypeConverters(BarcodeConverters::class)
abstract class CardKeeperDatabase: RoomDatabase() {
    abstract fun scannedCodeDao(): ScannedCodeDao
}
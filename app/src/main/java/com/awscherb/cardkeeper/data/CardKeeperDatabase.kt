package com.awscherb.cardkeeper.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.awscherb.cardkeeper.data.dao.PkPassDao
import com.awscherb.cardkeeper.data.dao.ScannedCodeDao
import com.awscherb.cardkeeper.data.entity.PkPassEntity
import com.awscherb.cardkeeper.data.entity.ScannedCodeEntity

@Database(
    entities = [
        ScannedCodeEntity::class,
        PkPassEntity::class,
    ],
    version = 22, exportSchema = false
)
@TypeConverters(com.awscherb.cardkeeper.util.db.TypeConverters::class)
abstract class CardKeeperDatabase : RoomDatabase() {
    abstract fun scannedCodeDao(): ScannedCodeDao
    abstract fun pkPassDao(): PkPassDao
}
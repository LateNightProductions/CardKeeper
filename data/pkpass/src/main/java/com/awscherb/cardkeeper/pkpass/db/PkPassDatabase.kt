package com.awscherb.cardkeeper.pkpass.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.awscherb.cardkeeper.pkpass.entity.PassUpdateEntity
import com.awscherb.cardkeeper.pkpass.entity.PkPassEntity

@Database(
    entities = [
        PkPassEntity::class,
        PassUpdateEntity::class
    ],
    version = 4, exportSchema = false
)
@TypeConverters(
    com.awscherb.cardkeeper.common.TypeConverters::class,
    PkPassConverters::class
)
abstract class PkPassDatabase : RoomDatabase() {
    abstract fun pkPassDao(): PkPassDao
}
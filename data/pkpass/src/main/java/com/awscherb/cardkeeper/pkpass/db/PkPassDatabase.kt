package com.awscherb.cardkeeper.pkpass.db

import androidx.room.AutoMigration
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
    version = 9,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 8, to = 9)
    ]
)
@TypeConverters(
    com.awscherb.cardkeeper.common.TypeConverters::class,
    PkPassConverters::class
)
abstract class PkPassDatabase : RoomDatabase() {
    abstract fun pkPassDao(): PkPassDao
}
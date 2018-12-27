package com.awscherb.cardkeeper.data

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration

object DatabaseMigrations {

    val MIGRATE_10_TO_11 = object : Migration(10, 11) {

        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE scannedCode ADD COLUMN created INTEGER NOT NULL DEFAULT 0")
        }

    }

}
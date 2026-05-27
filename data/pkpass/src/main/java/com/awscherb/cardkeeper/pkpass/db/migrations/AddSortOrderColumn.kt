package com.awscherb.cardkeeper.pkpass.db.migrations

import androidx.room.migration.Migration

val MIGRATION_9_10_ADD_SORT_ORDER = Migration(9, 10) {
    it.execSQL("ALTER TABLE PkPassEntity ADD COLUMN sortOrder INTEGER NOT NULL DEFAULT 0")
    it.execSQL("UPDATE PkPassEntity SET sortOrder = created")
}

package com.awscherb.cardkeeper.barcode.db.migrations

import androidx.room.migration.Migration

val MIGRATION_12_13_ADD_SORT_ORDER = Migration(12, 13) {
    it.execSQL("ALTER TABLE scannedCode ADD COLUMN sortOrder INTEGER NOT NULL DEFAULT 0")
    it.execSQL("UPDATE scannedCode SET sortOrder = created")
}

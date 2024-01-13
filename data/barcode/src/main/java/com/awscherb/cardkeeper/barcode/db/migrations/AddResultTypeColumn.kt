package com.awscherb.cardkeeper.barcode.db.migrations

import androidx.room.migration.Migration
import com.google.zxing.client.result.ParsedResultType

val MIGRATION_11_12_ADD_RESULT = Migration(11, 12) {
    it.execSQL(
        "ALTER TABLE scannedCode ADD COLUMN parsedType INTEGER NOT NULL DEFAULT(${
            ParsedResultType.TEXT
                .ordinal
        })"
    )
}
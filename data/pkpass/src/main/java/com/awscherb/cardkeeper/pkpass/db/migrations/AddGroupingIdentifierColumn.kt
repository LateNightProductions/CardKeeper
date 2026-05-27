package com.awscherb.cardkeeper.pkpass.db.migrations

import androidx.room.migration.Migration

val MIGRATION_10_11_ADD_GROUPING_IDENTIFIER = Migration(10, 11) {
    it.execSQL("ALTER TABLE PkPassEntity ADD COLUMN groupingIdentifier TEXT")
}

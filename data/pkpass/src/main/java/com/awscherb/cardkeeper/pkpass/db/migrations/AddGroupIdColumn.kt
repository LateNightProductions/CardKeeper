package com.awscherb.cardkeeper.pkpass.db.migrations

import androidx.room.migration.Migration

val MIGRATION_11_12_ADD_GROUP_ID = Migration(11, 12) {
    it.execSQL("ALTER TABLE PkPassEntity ADD COLUMN groupId TEXT")
}

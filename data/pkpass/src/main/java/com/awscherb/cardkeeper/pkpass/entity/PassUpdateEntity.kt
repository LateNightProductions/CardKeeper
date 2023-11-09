package com.awscherb.cardkeeper.pkpass.entity

import androidx.room.Entity

@Entity(primaryKeys = ["passId", "shouldAutoUpdate"])
data class PassUpdateEntity(
    val passId: String,
    val shouldAutoUpdate: Boolean
)
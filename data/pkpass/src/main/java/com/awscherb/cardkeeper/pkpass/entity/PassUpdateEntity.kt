package com.awscherb.cardkeeper.pkpass.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PassUpdateEntity(
    @PrimaryKey
    val passId: String,
    val shouldAutoUpdate: Boolean
)
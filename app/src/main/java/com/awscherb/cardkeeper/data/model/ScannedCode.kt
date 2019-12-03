package com.awscherb.cardkeeper.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.zxing.BarcodeFormat

@Entity(tableName = "scannedCode")
data class ScannedCode(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var format: BarcodeFormat,
    var text: String,
    var title: String,
    var created: Long = 0
) : BaseModel()
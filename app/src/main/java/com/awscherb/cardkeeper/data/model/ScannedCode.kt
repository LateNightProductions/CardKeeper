package com.awscherb.cardkeeper.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.zxing.BarcodeFormat

@Entity(tableName = "scannedCode")
class ScannedCode : BaseModel() {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    lateinit var format: BarcodeFormat
    lateinit var text: String
    lateinit var title: String
    var created: Long = 0
}
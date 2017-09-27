package com.awscherb.cardkeeper.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.zxing.BarcodeFormat

@Entity(tableName = "scannedCode")
class ScannedCode: BaseModel() {

    @PrimaryKey(autoGenerate = true) var id: Int = 0
    lateinit var format: BarcodeFormat
    lateinit var text: String
    lateinit var title: String
    var created: Long = 0
}
package com.awscherb.cardkeeper.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.awscherb.cardkeeper.data.model.SavedItem
import com.awscherb.cardkeeper.data.model.ScannedCodeModel
import com.google.zxing.BarcodeFormat

/**
 * User scanned code, QR, barcode, etc
 */
@Entity(tableName = "scannedCode")
data class ScannedCodeEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    override val format: BarcodeFormat,
    override val text: String,
    override val title: String,
    override val created: Long
) : ScannedCodeModel
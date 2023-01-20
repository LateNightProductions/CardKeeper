package com.awscherb.cardkeeper.data.model

import com.google.zxing.BarcodeFormat

interface ScannedCodeModel : SavedItem {
    val id: Int
    val format: BarcodeFormat
    val text: String
    val title: String
}

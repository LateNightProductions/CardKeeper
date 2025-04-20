package com.awscherb.cardkeeper.barcode.model

import com.awscherb.cardkeeper.core.SavedItem
import com.awscherb.cardkeeper.types.BarcodeFormat
import com.awscherb.cardkeeper.types.ParsedResultType

interface ScannedCodeModel : SavedItem {
    override val id: Int
    val format: BarcodeFormat
    val text: String
    val title: String
    val parsedType: ParsedResultType
}
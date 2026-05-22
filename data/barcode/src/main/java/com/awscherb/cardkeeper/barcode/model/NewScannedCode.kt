package com.awscherb.cardkeeper.barcode.model

import com.awscherb.cardkeeper.types.BarcodeFormat
import com.awscherb.cardkeeper.types.ParsedResultType

data class NewScannedCode(
    val format: BarcodeFormat,
    val text: String,
    val title: String,
    val created: Long,
    val parsedType: ParsedResultType
)

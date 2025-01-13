package com.awscherb.cardkeeper.create

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.result.ParsedResultType

data class CreateModel(
    val title: String,
    val text: String,
    val format: BarcodeFormat,
    val parsedType: ParsedResultType = ParsedResultType.TEXT
)

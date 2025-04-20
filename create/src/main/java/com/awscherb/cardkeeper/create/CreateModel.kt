package com.awscherb.cardkeeper.create

import com.awscherb.cardkeeper.types.BarcodeFormat
import com.awscherb.cardkeeper.types.ParsedResultType

data class CreateModel(
    val title: String,
    val text: String,
    val format: BarcodeFormat,
    val parsedType: ParsedResultType = ParsedResultType.TEXT
)

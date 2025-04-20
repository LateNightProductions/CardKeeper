package com.awscherb.cardkeeper.codedetail

import com.awscherb.cardkeeper.types.BarcodeFormat
import com.awscherb.cardkeeper.types.ParsedResultType

data class CodeDetailModel(
    val title: String,
    val format: BarcodeFormat,
    val text: String,
    val parsedType: ParsedResultType
)
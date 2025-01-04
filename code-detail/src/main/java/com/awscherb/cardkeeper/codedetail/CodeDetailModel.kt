package com.awscherb.cardkeeper.codedetail

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.result.ParsedResultType

data class CodeDetailModel(
    val title: String,
    val format: BarcodeFormat,
    val text: String,
    val parsedType: ParsedResultType
)
package com.awscherb.cardkeeper.items

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.result.ParsedResultType

data class ScannedCodeItemModel(
    override val id: String,
    val title: String,
    val barcodeFormat: BarcodeFormat,
    val message: String,
    val parsedType: ParsedResultType,
    override val created: Long
) : ItemModel
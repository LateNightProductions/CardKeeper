package com.awscherb.cardkeeper.items.model

import com.awscherb.cardkeeper.types.BarcodeFormat
import com.awscherb.cardkeeper.types.ParsedResultType


data class ScannedCodeItemModel(
    override val id: String,
    val title: String,
    val barcodeFormat: BarcodeFormat,
    val message: String,
    val parsedType: ParsedResultType,
    override val created: Long
) : ItemModel
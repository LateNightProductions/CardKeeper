package com.awscherb.cardkeeper.passdetail.util

import androidx.compose.ui.graphics.Color
import com.awscherb.cardkeeper.passUi.FieldConfig
import com.awscherb.cardkeeper.passdetail.model.PassDetailModel

val SampleEvent = PassDetailModel(
    id = "id",
    canBeUpdated = true,
    type = PassDetailModel.Type.EVENT_TICKET,
    backgroundColor = Color.Red,
    backgroundPath = null,
    logoPath = null,
    description = "Your Event",
    foregroundColor = Color.White,
    labelColor = Color.White,
    logoText = "Logo",
    barcodes = listOf(),
    isBarcodeSquare = false,
    footerPath = null,
    headerItems = listOf(
        FieldConfig("header", "value", Color.White),
    ),
    primaryFields = listOf(FieldConfig("primary", "value", Color.White)),
    auxiliaryFields = listOf(FieldConfig("aux", "value", Color.White)),
    secondaryFields = listOf(FieldConfig("second", "value", Color.White)),
    transit = null,
    thumbnailPath = null,
    stripPath = null,
    backItems = listOf(),
    webServiceUrl = null,
    identifier = null,
    authenticationToken = null
)
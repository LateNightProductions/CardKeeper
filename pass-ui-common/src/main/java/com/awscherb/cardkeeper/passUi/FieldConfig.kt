package com.awscherb.cardkeeper.passUi

import androidx.compose.ui.graphics.Color

data class FieldConfig(
    val label: String?,
    val value: String,
    val labelColor: Color,
    val valueColor: Color = labelColor
)
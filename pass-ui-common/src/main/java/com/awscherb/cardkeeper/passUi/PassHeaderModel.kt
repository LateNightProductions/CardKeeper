package com.awscherb.cardkeeper.passUi

import androidx.compose.ui.graphics.Color

data class PassHeaderModel(
    val logo: String?,
    val logoText: String?,
    val description: String?,
    val foregroundColor: Color,
    val labelColor: Color,
    val headerConfig: List<FieldConfig>
)

package com.awscherb.cardkeeper.ui.view

data class FieldConfig(
    val label: String?,
    val value: String,
    val labelColor: Int,
    val valueColor: Int = labelColor
)
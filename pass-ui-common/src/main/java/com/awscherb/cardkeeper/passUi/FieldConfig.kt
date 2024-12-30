package com.awscherb.cardkeeper.passUi

data class FieldConfig(
    val label: String?,
    val value: String,
    val labelColor: Int,
    val valueColor: Int = labelColor
)
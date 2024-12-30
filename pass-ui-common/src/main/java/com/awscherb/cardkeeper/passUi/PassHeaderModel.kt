package com.awscherb.cardkeeper.passUi

data class PassHeaderModel(
    val logo: String?,
    val logoText: String?,
    val description: String?,
    val foregroundColor: Int,
    val labelColor: Int,
    val headerConfig: List<FieldConfig>
)

package com.awscherb.cardkeeper.items.model

import androidx.compose.ui.graphics.Color
import com.awscherb.cardkeeper.passUi.PassHeaderModel

data class PassItemModel(
    override val id: String,
    val backgroundColor: Color,
    val backgroundPath: String? = null,
    val header: PassHeaderModel,
    val isEvent: Boolean = false,
    override val created: Long
) : ItemModel
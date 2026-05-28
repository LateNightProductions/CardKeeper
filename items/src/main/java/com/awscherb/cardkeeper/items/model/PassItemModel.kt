package com.awscherb.cardkeeper.items.model

import androidx.compose.ui.graphics.Color
import com.awscherb.cardkeeper.passUi.PassHeaderModel
import com.awscherb.cardkeeper.pkpass.model.PassInfoType

data class PassItemModel(
    override val id: String,
    val backgroundColor: Color,
    val backgroundPath: String? = null,
    val header: PassHeaderModel,
    val isEvent: Boolean = false,
    val passInfoType: PassInfoType? = null,
    override val created: Long,
    override val sortOrder: Long
) : ItemModel
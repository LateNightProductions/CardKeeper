package com.awscherb.cardkeeper.items

import com.awscherb.cardkeeper.passUi.PassHeaderModel

data class PassItemModel(
    override val id: String,
    val backgroundColor: Int,
    val backgroundPath: String? = null,
    val header: PassHeaderModel,
    val isEvent: Boolean = false,
    override val created: Long
) : ItemModel
package com.awscherb.cardkeeper.items.model

data class GroupedPassItemModel(
    override val id: String,
    val passes: List<PassItemModel>,
    override val created: Long,
    override val sortOrder: Long
) : ItemModel

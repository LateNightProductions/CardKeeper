package com.awscherb.cardkeeper.items.model

sealed interface ItemModel {
    val id: String
    val created: Long
}
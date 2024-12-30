package com.awscherb.cardkeeper.items

sealed interface ItemModel {
    val id: String
    val created: Long
}
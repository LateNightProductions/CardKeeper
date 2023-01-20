package com.awscherb.cardkeeper.data.model

sealed interface SavedItem {

    val created: Long
}
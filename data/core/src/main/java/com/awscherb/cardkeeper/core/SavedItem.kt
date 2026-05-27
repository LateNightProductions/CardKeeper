package com.awscherb.cardkeeper.core

interface SavedItem {

    val id: Any
    val created: Long
    val sortOrder: Long
}
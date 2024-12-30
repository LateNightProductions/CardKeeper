package com.awscherb.cardkeeper.types

interface Barcode {
    val altText: String?
    val format: String
    val message: String
    val messageEncoding: String
}
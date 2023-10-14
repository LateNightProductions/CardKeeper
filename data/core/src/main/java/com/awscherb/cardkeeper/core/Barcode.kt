package com.awscherb.cardkeeper.core

interface Barcode {
    val altText: String?
    val format: String
    val message: String
    val messageEncoding: String
}
package com.awscherb.cardkeeper.data.model

interface PkPassModel : SavedItem {
    val id: Int
    val description: String
    val organizationName: String
    val barcode: Barcode?
    val barcodes: List<Barcode>?
    val passTypeIdentifier: String
    val serialNumber: String
    val backgroundColor: String?
    val foregroundColor: String?
    val labelColor: String?
}

interface Barcode {
    val altText: String?
    val format: String
    val message: String
    val messageEncoding: String
}

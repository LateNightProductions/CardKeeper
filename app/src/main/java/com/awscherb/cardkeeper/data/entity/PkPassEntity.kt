package com.awscherb.cardkeeper.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.awscherb.cardkeeper.data.model.Barcode
import com.awscherb.cardkeeper.data.model.PkPassModel

@Entity
data class PkPassEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    override val description: String,
    override val organizationName: String,
    @Embedded(prefix = "barcode_")
    override val barcode: BarcodeStruct?,
    override val barcodes: List<BarcodeStruct>? = emptyList(),
    override val passTypeIdentifier: String,
    override val serialNumber: String,
    // ex rgb(23, 187, 82)
    override val backgroundColor: String?,
    override val foregroundColor: String?,
    override val labelColor: String?,
    // local attr
    override val created: Long
) : PkPassModel

data class BarcodeStruct(
    override val altText: String?,
    override val format: String,
    override val message: String,
    override val messageEncoding: String
) : Barcode


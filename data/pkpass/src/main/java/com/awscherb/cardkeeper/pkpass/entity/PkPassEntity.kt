package com.awscherb.cardkeeper.pkpass.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.awscherb.cardkeeper.core.Barcode
import com.awscherb.cardkeeper.pkpass.model.PassInfo
import com.awscherb.cardkeeper.pkpass.model.FieldObject
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import java.util.Date

@Entity
data class PkPassEntity(
    @PrimaryKey
    override var id: String = "",
    override val description: String,
    override val organizationName: String,
    @Embedded(prefix = "barcode_")
    override val barcode: BarcodeStruct?,
    override val barcodes: List<BarcodeStruct>? = emptyList(),
    override val passTypeIdentifier: String,
    override val serialNumber: String,
    override val logoText: String?,
    override val expirationDate: Date?,

    // ex rgb(23, 187, 82)
    override val backgroundColor: String?,
    override val foregroundColor: String?,
    override val labelColor: String?,
    // local attr
    override val created: Long,
    override val boardingPass: PassInfoStruct?,
    override val storeCard: PassInfoStruct?,
    override val generic: PassInfoStruct?,
    override val eventTicket: PassInfoStruct?,
    override val coupon: PassInfoStruct?,

    override var logoPath: String?,
    override var stripPath: String?,
    override var footerPath: String?,
    override val backgroundPath: String?,

    override var translation: Map<String, String>?,
    override val webServiceURL: String?,
    override val authenticationToken: String?

) : PkPassModel

data class BarcodeStruct(
    override val altText: String?,
    override val format: String,
    override val message: String,
    override val messageEncoding: String
) : Barcode

data class PassInfoStruct(
    override val transitType: String?,
    override val headerFields: List<FieldObject>?,
    override val primaryFields: List<FieldObject>?,
    override val secondaryFields: List<FieldObject>?,
    override val auxiliaryFields: List<FieldObject>?,
    override val backFields: List<FieldObject>?,
) : PassInfo


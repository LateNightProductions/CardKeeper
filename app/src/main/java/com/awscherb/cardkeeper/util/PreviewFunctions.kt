package com.awscherb.cardkeeper.util

import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.core.Barcode
import com.awscherb.cardkeeper.pkpass.model.FieldObject
import com.awscherb.cardkeeper.pkpass.model.PassInfo
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.util.BarcodeConstants
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.result.ParsedResultType
import java.util.Date

fun createPassModel(
    id: String = "",
    description: String = "",
    orgName: String = "",
    barcode: Barcode? = null,
    barcodes: List<Barcode>? = null,
    passTypeId: String = "",
    logoText: String? = null,
    expirationDate: Date? = null,
    relevantDate: Date? = null,
    backgroundColor: String? = null,
    foregroundColor: String? = null,
    labelColor: String? = null,
    boardingPass: PassInfo? = null,
    storeCard: PassInfo? = null,
    generic: PassInfo? = null,
    eventTicket: PassInfo? = null,
    coupon: PassInfo? = null,
    logoPath: String? = null,
    stripPath: String? = "",
    footerPath: String? = null,
    backgroundPath: String? = null,
    thumbnailPath: String? = null,
    serialNumber: String? = "",
    webServiceURL: String? = "",
    authenticationToken: String? = "",
    translation: Map<String, String>? = null,
    created: Long = 0L
) = object : PkPassModel {
    override val id: String = id
    override val description: String = description
    override val organizationName: String = orgName
    override val barcode: Barcode? = barcode
    override val barcodes: List<Barcode>? = barcodes
    override val passTypeIdentifier: String = passTypeId
    override val logoText: String? = logoText
    override val expirationDate: Date? = expirationDate
    override val relevantDate: Date? = relevantDate
    override val backgroundColor: String? = backgroundColor
    override val foregroundColor: String? = foregroundColor
    override val labelColor: String? = labelColor
    override val boardingPass: PassInfo? = boardingPass
    override val storeCard: PassInfo? = storeCard
    override val generic: PassInfo? = generic
    override val eventTicket: PassInfo? = eventTicket
    override val coupon: PassInfo? = coupon
    override val logoPath: String? = logoPath
    override val stripPath: String? = stripPath
    override val footerPath: String? = footerPath
    override val backgroundPath: String? = backgroundPath
    override val thumbnailPath: String? = thumbnailPath
    override val serialNumber: String? = serialNumber
    override val webServiceURL: String? = webServiceURL
    override val authenticationToken: String? = authenticationToken
    override val translation: Map<String, String>? = translation
    override val created: Long = created
}

fun createPassInfo(
    transitType: String? = null,
    headerFields: List<FieldObject>? = emptyList(),
    primaryFields: List<FieldObject>? = emptyList(),
    secondaryFields: List<FieldObject>? = emptyList(),
    auxiliaryFields: List<FieldObject>? = emptyList(),
    backFields: List<FieldObject>? = emptyList(),
) = object : PassInfo {
    override val transitType: String? = transitType
    override val headerFields: List<FieldObject>? = headerFields
    override val primaryFields: List<FieldObject>? = primaryFields
    override val secondaryFields: List<FieldObject>? = secondaryFields
    override val auxiliaryFields: List<FieldObject>? = auxiliaryFields
    override val backFields: List<FieldObject>? = backFields
}

fun createScannedCode(
    id: Int = 0,
    format: BarcodeFormat = BarcodeFormat.QR_CODE,
    text: String = "text",
    title: String = "title",
    created: Long = 0L,
    parsedType: ParsedResultType = ParsedResultType.TEXT
) = object : ScannedCodeModel {
    override val id: Int = id
    override val format: BarcodeFormat = format
    override val text: String = text
    override val title: String = title
    override val created: Long = created
    override val parsedType: ParsedResultType = ParsedResultType.TEXT
}

fun createBarcode(
    altText: String? = null,
    format: String = BarcodeConstants.FORMAT_QR,
    message: String = "message",
    messageEncoding: String = "UTF-8"
) = object : Barcode {
    override val altText = altText
    override val format = format
    override val message = message
    override val messageEncoding = messageEncoding
}

val SampleContact = """
            BEGIN:VCARD
            VERSION:3.0
            N:Lastname;Firstname
            FN:Firstname Lastname
            ORG:CompanyName
            TITLE:JobTitle
            ADR:;;123 Sesame St;SomeCity;CA;12345;USA
            TEL;WORK;VOICE:1234567890
            TEL;CELL:Mobile
            TEL;FAX:
            EMAIL;WORK;INTERNET:foo@email.com
            URL:http://website.com
            END:VCARD
            """.trimIndent()

val SampleWifi = "WIFI:T:WPA;S:network_name;P:passsw0rd;H:;"
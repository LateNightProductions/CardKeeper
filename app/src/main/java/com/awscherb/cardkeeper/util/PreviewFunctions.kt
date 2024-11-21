package com.awscherb.cardkeeper.util

import android.content.res.Configuration
import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.core.Barcode
import com.awscherb.cardkeeper.pkpass.model.FieldObject
import com.awscherb.cardkeeper.pkpass.model.PassInfo
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.util.BarcodeConstants
import com.awscherb.cardkeeper.pkpass.util.TransitConstants
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.result.ParsedResultType

fun createPassModel(
    id: String = "",
    description: String = "",
    orgName: String = "",
    barcode: Barcode? = null,
    barcodes: List<Barcode>? = null,
    passTypeId: String = "",
    logoText: String? = null,
    expirationDate: String? = null,
    relevantDate: String? = null,
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
    override val expirationDate: String? = expirationDate
    override val relevantDate: String? = relevantDate
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

const val GlobalPreviewNightMode = Configuration.UI_MODE_NIGHT_YES

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

val SampleLicense = """
    @
    ANSI 1234
    DCTFirstname
    DCSLastname
    DBD02082024
    DBB09011993
    DBA09012030
    DAG123 Fake Street
    DAINew York
    DAJNY
    DAK10001
""".trimIndent()

val SampleWifi = "WIFI:T:WPA;S:network_name;P:passsw0rd;H:;"

val SampleTel = "TEL:3125550690"

val SampleEmail = "mailto:recip@example.com?cc=other@example.com&subject=hello&body=email body"

val SampleFlight = createPassModel(
    backgroundColor = "rgb(50,168,96)",
    foregroundColor = "rgb(255,255,255)",
    labelColor = "rgb(255,255,255)",
    footerPath = "asdf",
    logoText = "Flight",
    barcode = createBarcode(),
    boardingPass = createPassInfo(
        transitType = TransitConstants.TYPE_AIR,
        headerFields = listOf(
            FieldObject("key", "FLIGHT", "DL123"),
            FieldObject("key", "GATE", "50")
        ),
        primaryFields = listOf(
            FieldObject("origin", "NEW YORK", "JFK"),
            FieldObject("destination", "LOS ANGELES", "LAX")
        ),
        auxiliaryFields = listOf(
            FieldObject("terminal", "TERMINAL", "4"),
            FieldObject("gate", "GATE", "50"),
            FieldObject("seat", "SEAT", "10A"),
            FieldObject("zone", "ZONE", "1"),
        ),
        secondaryFields = listOf(
            FieldObject("passenger", "PASSENGER", "Joe Android"),
            FieldObject("boardingTime", "BOARDING", "12:00 PM"),
        )
    )
)

val SampleGenericTravel = createPassModel(
    backgroundColor = "rgb(87,28,220)",
    foregroundColor = "rgb(255,255,255)",
    labelColor = "rgb(255,255,255)",
    barcode = createBarcode(
        altText = "Alt Text",
        format = BarcodeConstants.FORMAT_PDF_417
    ),
    boardingPass = createPassInfo(
        transitType = "other",
        headerFields = listOf(
            FieldObject("key", "TRACK", "4")
        ),
        primaryFields = listOf(
            FieldObject("origin", "NYP", "PENN"),
            FieldObject("destination", "EWR", "EWR")
        ),
        auxiliaryFields = listOf(
            FieldObject("terminal", "TRACK", "4"),
        ),
        secondaryFields = listOf(
            FieldObject("boardingTime", "BOARDING", "12:00 PM"),
        )
    )
)

val SampleStorePass = createPassModel(
    backgroundColor = "rgb(219, 235, 52)",
    foregroundColor = "rgb(0,0,0)",
    labelColor = "rgb(0,0,0)",
    barcode = createBarcode(
        altText = "1234",
        format = BarcodeConstants.FORMAT_QR
    ),
    storeCard = createPassInfo(
        primaryFields = listOf(
            FieldObject(
                "balance", "remaining balance", "21.75"
            )
        ),
        headerFields = listOf(
            FieldObject("key", null, "Loyalty")
        ),
        auxiliaryFields = listOf(
            FieldObject("accountNumber", "Account Number", "1234"),
        ),
        secondaryFields = listOf(
            FieldObject("expiresOn", "Expires On", "12/31/23"),
        )
    )
)

val SampleEvent = createPassModel(
    backgroundColor = "rgb(219, 235, 52)",
    foregroundColor = "rgb(0,0,0)",
    webServiceURL = "wer",
    labelColor = "rgb(0,0,0)",
    barcode = createBarcode(
        altText = "1234",
        format = BarcodeConstants.FORMAT_PDF_417
    ),
    eventTicket = createPassInfo(
        auxiliaryFields = listOf(
            FieldObject("Visit Date", "Visit Date", "4/3/2024"),
            FieldObject("Time", "Time", "12:00 PM"),
        ),
        secondaryFields = listOf(
            FieldObject("Details", "Details", "Details On Back"),
        )
    )
)
val SampleEvent2 = createPassModel(
    backgroundColor = "rgb(219, 235, 52)",
    foregroundColor = "rgb(0,0,0)",
    labelColor = "rgb(0,0,0)",
    backgroundPath = "path",

    stripPath = null,
    barcode = createBarcode(
        altText = "1234",
        format = BarcodeConstants.FORMAT_PDF_417
    ),
    eventTicket = createPassInfo(
        primaryFields = listOf(
            FieldObject("Event", "Event", "Cool Event"),
        ),
        secondaryFields = listOf(
            FieldObject("Location", "Location", "Venue"),
        )
    )
)

val SamplePdfPass = createPassModel(
    backgroundColor = "rgb(200, 50, 43)",
    foregroundColor = "rgb(0,0,0)",
    labelColor = "rgb(0,0,0)",
    barcode = createBarcode(
        altText = "1234",
        format = BarcodeConstants.FORMAT_CODE_128
    ),
    storeCard = createPassInfo(
        headerFields = listOf(
            FieldObject("key", null, "Loyalty")
        ),
        auxiliaryFields = listOf(
            FieldObject("accountNumber", "Account Number", "1234"),
        ),
        secondaryFields = listOf(
            FieldObject("expiresOn", "Expires On", "12/31/23"),
        )
    )
)

val SampleGenericPass = createPassModel(
    backgroundColor = "rgb(128, 200, 45)",
    foregroundColor = "rgb(0,0,0)",
    labelColor = "rgb(0,0,0)",
    barcode = createBarcode(
        altText = "1234",
        format = BarcodeConstants.FORMAT_PDF_417
    ),
    generic = createPassInfo(
        headerFields = listOf(
            FieldObject("key", null, "Card Keeper Giant Card")
        ),
        primaryFields = listOf(
            FieldObject("key", null, "Card Keeper Giant Card")
        ),
        auxiliaryFields = listOf(
            FieldObject("accountNumber", "Account Number", "1234"),
        ),
        secondaryFields = listOf(
            FieldObject("expiresOn", "Expires On", "12/31/23"),
        )
    )
)
val SampleGenericNoBarcodePass = createPassModel(
    backgroundColor = "rgb(128, 200, 45)",
    foregroundColor = "rgb(0,0,0)",
    labelColor = "rgb(0,0,0)",
    generic = createPassInfo(
        headerFields = listOf(
            FieldObject("key", null, "Card Keeper Giant Card")
        ),
        primaryFields = listOf(
            FieldObject("key", null, "Card Keeper Giant Card")
        ),
        auxiliaryFields = listOf(
            FieldObject("accountNumber", "Account Number", "1234"),
        ),
        secondaryFields = listOf(
            FieldObject("expiresOn", "Expires On", "12/31/23"),
        )
    )
)

val SampleGenericPass2 = createPassModel(
    backgroundColor = "rgb(128, 200, 45)",
    foregroundColor = "rgb(0,0,0)",
    labelColor = "rgb(0,0,0)",
    barcode = createBarcode(
        format = BarcodeConstants.FORMAT_PDF_417
    ),
    generic = createPassInfo(
        headerFields = listOf(
            FieldObject("key", null, "Card Keeper Giant Card")
        ),
        primaryFields = listOf(
            FieldObject("key", null, "Card Keeper Giant Card")
        ),
        auxiliaryFields = listOf(
            FieldObject("accountNumber", "Account Number", "1234"),
        ),
        secondaryFields = listOf(
            FieldObject("expiresOn", "Expires On", "12/31/23"),
        )
    )
)


val SampleCoupon = createPassModel(
    backgroundColor = "rgb(128, 200, 45)",
    foregroundColor = "rgb(0,0,0)",
    labelColor = "rgb(0,0,0)",
    stripPath = null,
    barcode = createBarcode(
        format = BarcodeConstants.FORMAT_PDF_417
    ),
    coupon = createPassInfo(
        primaryFields = listOf(
            FieldObject("Offer", "Offer", "20% off")
        ),
        auxiliaryFields = listOf(
            FieldObject("expires", "Expires", "Tomorrow"),
        ),
    )
)
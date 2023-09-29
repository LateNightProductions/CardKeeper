package com.awscherb.cardkeeper.pkpass.model

import android.graphics.Color
import com.awscherb.cardkeeper.core.SavedItem
import com.google.zxing.BarcodeFormat
import okhttp3.internal.toHexString
import java.util.Date

interface PkPassModel : SavedItem {
    val id: String
    val description: String
    val organizationName: String
    val barcode: Barcode?
    val barcodes: List<Barcode>?
    val passTypeIdentifier: String
    val logoText: String?

    val expirationDate: Date?

    // rgb(0, 187, 82)
    val backgroundColor: String?
    val foregroundColor: String?
    val labelColor: String?

    val boardingPass: PassInfo?
    val storeCard: PassInfo?
    val generic: PassInfo?
    val eventTicket: PassInfo?

    val logoPath: String?
    val stripPath: String?
    val footerPath: String?

    // WebService -related fields
    val serialNumber: String?
    val webServiceURL: String?
    val authenticationToken: String?

    val translation: Map<String, String>?
}

interface PassInfo {
    val transitType: String?
    val headerFields: List<FieldObject>?
    val primaryFields: List<FieldObject>?
    val secondaryFields: List<FieldObject>?
    val auxiliaryFields: List<FieldObject>?
    val backFields: List<FieldObject>?
}

interface Barcode {
    val altText: String?
    val format: String
    val message: String
    val messageEncoding: String
}

data class FieldObject(
    val key: String,
    val label: String?,
    val value: String,
)

fun String.toBarcodeFormat() =
    when (this) {
        "PKBarcodeFormatQR" -> BarcodeFormat.QR_CODE
        "PKBarcodeFormatPDF417" -> BarcodeFormat.PDF_417
        "PKBarcodeFormatAztec" -> BarcodeFormat.AZTEC
        "PKBarcodeFormatCode128" -> BarcodeFormat.CODE_128
        else -> throw IllegalArgumentException("Unknown type $this")
    }

enum class TransitType {
    AIR
}

fun PassInfo.isTransit() = transitType != null
fun PassInfo.getTransitType() = transitType?.getTransitType()

fun PassInfo.findOriginDestination(): Pair<FieldObject, FieldObject> {
    val origin = primaryFields?.find { it.key.equals("origin", true) } ?: primaryFields?.get(0)
    ?: throw IllegalArgumentException("cannot find origin $primaryFields")

    val dest = primaryFields?.find { it.key.equals("destination", true) } ?: primaryFields?.get(1)
    ?: throw IllegalArgumentException("cannot find destination $primaryFields")

    return origin to dest
}


fun PkPassModel.getTranslatedLabel(label: String?): String? {
    val tr = translation
    return when {
        tr != null -> tr[label] ?: label
        else -> label
    }
}

fun PkPassModel.getTranslatedValue(label: String): String {
    val tr = translation
    return when {
        tr != null -> tr[label] ?: label
        else -> label
    }
}

fun String?.getTransitType(): TransitType? =
    when (this) {
        "PKTransitTypeAir" -> TransitType.AIR
        else -> null
    }

fun String?.parseHexColor(): Int {
    return Color.parseColor(when (this) {
        null -> "#000000"
        else -> {
            if (this.startsWith("#")) {
                this
            } else {
                val parse = subSequence(indexOf("(") + 1, indexOf(")"))

                val numbers = parse.split(",")

                val ints = numbers.subList(0, 3).map { it.trim().toInt() }
                val hex = ints.map { Integer.toHexString(it) }
                val padded = hex.map { if (it.length == 1) "0$it" else it }

                // Check for alpha - format rgba(r, g, b, a), or just use full opacity
                val alpha = if (numbers.size == 4) {
                    val alphaPercent = numbers[3].toFloat()
                    (255 * alphaPercent).toInt().toHexString()
                } else {
                    "FF"
                }

                val sb = StringBuilder()
                padded.forEach { sb.append(it) }
                "#$alpha$sb"
            }
        }
    })
}

fun PkPassModel.findPassInfo(): PassInfo? = when {
    boardingPass != null -> boardingPass
    storeCard != null -> storeCard
    generic != null -> generic
    eventTicket != null -> eventTicket
    else -> null
}

fun PkPassModel.canBeUpdated() = !this.webServiceURL.isNullOrEmpty()
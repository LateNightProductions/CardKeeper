package com.awscherb.cardkeeper.pkpass.model

import android.graphics.Color
import android.text.format.DateUtils
import com.awscherb.cardkeeper.core.Barcode
import com.awscherb.cardkeeper.core.SavedItem
import com.awscherb.cardkeeper.pkpass.util.BarcodeConstants
import com.awscherb.cardkeeper.pkpass.util.PassDateUtils
import com.awscherb.cardkeeper.pkpass.util.TransitConstants
import com.google.zxing.BarcodeFormat
import okhttp3.internal.toHexString
import java.util.Date

interface PkPassModel : SavedItem {
    override val id: String
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
    val coupon: PassInfo?

    val logoPath: String?
    val stripPath: String?
    val footerPath: String?
    val backgroundPath: String?

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


data class FieldObject(
    val key: String,
    val label: String?,
    val value: String,
    val dateStyle: String? = null
) {
    val hasDate get() = dateStyle != null

    val typedValue: String
        get() = when {
            hasDate -> {
                val date = PassDateUtils.timezoneFormat.parse(value) ?: Date()
                PassDateUtils.shortDateFormat.format(date)
            }
            else -> value
        }

}

fun PkPassModel.findFirstBarcode(): Barcode? {
    return barcode ?: barcodes?.first()
}


fun String.toBarcodeFormat() =
    when (this) {
        BarcodeConstants.FORMAT_QR -> BarcodeFormat.QR_CODE
        BarcodeConstants.FORMAT_PDF_417 -> BarcodeFormat.PDF_417
        BarcodeConstants.FORMAT_AZTEC -> BarcodeFormat.AZTEC
        BarcodeConstants.FORMAT_CODE_128 -> BarcodeFormat.CODE_128
        else -> throw IllegalArgumentException("Unknown type $this")
    }

enum class TransitType {
    AIR,
    BOAT,
    BUS,
    GENERIC,
    TRAIN
}

fun PassInfo.isTransit() = transitType != null
fun PassInfo.getTransitType() = transitType?.getTransitType() ?: TransitType.GENERIC

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

fun String?.getTransitType(): TransitType =
    when (this) {
        TransitConstants.TYPE_AIR -> TransitType.AIR
        TransitConstants.TYPE_BOAT -> TransitType.BOAT
        TransitConstants.TYPE_BUS -> TransitType.BUS
        TransitConstants.TYPE_TRAIN -> TransitType.TRAIN
        else -> TransitType.GENERIC
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
    coupon != null -> coupon
    else -> null
}

val PkPassModel.passInfoType: PassInfoType?
    get() = when {
        boardingPass != null -> PassInfoType.BOARDING_PASS
        storeCard != null -> PassInfoType.STORE_CARD
        generic != null -> PassInfoType.GENERIC
        eventTicket != null -> PassInfoType.EVENT_TICKET
        coupon != null -> PassInfoType.COUPON
        else -> null
    }

enum class PassInfoType {
    BOARDING_PASS,
    COUPON,
    EVENT_TICKET,
    GENERIC,
    STORE_CARD,
}

fun PkPassModel.canBeUpdated() = !this.webServiceURL.isNullOrEmpty()
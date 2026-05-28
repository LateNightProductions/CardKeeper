package com.awscherb.cardkeeper.items.model

import com.awscherb.cardkeeper.pkpass.model.PassInfoType

sealed class FilterOptions(val label: String) {
    data object All : FilterOptions("Show all")
    data object QrCodes : FilterOptions("QR codes & barcodes")
    data class PassType(val type: PassInfoType) : FilterOptions(type.filterLabel)
}

val PassInfoType.filterLabel: String
    get() = when (this) {
        PassInfoType.BOARDING_PASS -> "Boarding passes"
        PassInfoType.COUPON -> "Coupons"
        PassInfoType.EVENT_TICKET -> "Event tickets"
        PassInfoType.GENERIC -> "Generic passes"
        PassInfoType.STORE_CARD -> "Store cards"
    }

package com.awscherb.cardkeeper.items.model

sealed class FilterOptions(val label: String) {

    data object All : FilterOptions("Show all")
    data object QrCodes : FilterOptions("Show only QR codes and barcodes")
    data object Passes : FilterOptions("Show only passes")

}
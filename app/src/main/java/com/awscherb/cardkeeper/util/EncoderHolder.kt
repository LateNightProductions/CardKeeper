package com.awscherb.cardkeeper.util

import com.journeyapps.barcodescanner.BarcodeEncoder

object EncoderHolder {
    val encoder: BarcodeEncoder = BarcodeEncoder()
}
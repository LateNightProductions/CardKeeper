package com.awscherb.cardkeeper.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

private object EncoderHolder {
    val encoder by lazy {
        BarcodeEncoder()
    }
}

@Composable
fun ScannedCodeImage(cardItem: ScannedCode) {
    val scaleType = when (cardItem.format) {
        BarcodeFormat.QR_CODE, BarcodeFormat.AZTEC, BarcodeFormat.DATA_MATRIX -> ContentScale.Fit
        else -> ContentScale.FillBounds
    }
    Image(
        bitmap = EncoderHolder.encoder.encodeBitmap(
            cardItem.text, cardItem.format, 200.dp.value.toInt(), 200.dp.value.toInt()
        ).asImageBitmap(),
        contentDescription = cardItem.text,
        contentScale = scaleType,
        modifier = Modifier
            .height(200.dp)
            .then(Modifier.fillMaxWidth())
            .padding(16.dp)
    )
}
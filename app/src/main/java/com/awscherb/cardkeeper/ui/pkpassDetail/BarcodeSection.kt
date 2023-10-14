package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.pkpass.model.Barcode
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
import com.awscherb.cardkeeper.pkpass.model.toBarcodeFormat
import com.awscherb.cardkeeper.pkpass.util.BarcodeConstants
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.util.EncoderHolder.encoder
import com.awscherb.cardkeeper.util.createBarcode
import com.awscherb.cardkeeper.util.createPassModel
import com.journeyapps.barcodescanner.BarcodeEncoder

@Composable
fun BarcodeSection(
    pass: PkPassModel,
    barcode: Barcode,
    size: Size,
    modifier: Modifier = Modifier,
) {
    val bitmap = encoder.encodeBitmap(
        barcode.message,
        barcode.format.toBarcodeFormat(),
        size.width.toInt() / 2,
        size.width.toInt() / 2
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "",
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    start = 8.dp,
                    end = 8.dp,
                    bottom = if (barcode.altText.isNullOrEmpty()) 8.dp else 0.dp,
                )
        )

        barcode.altText?.let { alt ->
            Text(
                modifier = Modifier
                    .padding(
                        vertical = 4.dp,
                        horizontal = 4.dp
                    )
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                text = alt,
                textAlign = TextAlign.Center,
                color = Color(pass.foregroundColor.parseHexColor()),
            )

        }
    }
}

@Composable
@Preview
fun QrCodePreview() {
    CardKeeperTheme {
        BarcodeSection(
            barcode = createBarcode(
                altText = null,
                format = BarcodeConstants.FORMAT_QR,
                message = "message",
                messageEncoding = "UTF-8",
            ),
            size = Size(500f, 500f),
            pass = createPassModel(foregroundColor = "rgb(255,255,255)")
        )
    }
}

@Composable
@Preview
fun QrCodeAltPreview() {
    CardKeeperTheme {
        BarcodeSection(
            barcode = createBarcode(
                altText = "message",
                format = BarcodeConstants.FORMAT_QR,
                message = "message",
                messageEncoding = "UTF-8",
            ),
            size = Size(500f, 500f),
            pass = createPassModel(foregroundColor = "rgb(0,0,255)")
        )
    }
}

@Composable
@Preview
fun Pdf417Preview() {
    CardKeeperTheme {
        BarcodeSection(
            barcode = createBarcode(
                altText = "message",
                format = BarcodeConstants.FORMAT_PDF_417,
                message = "message",
                messageEncoding = "UTF-8",
            ),
            size = Size(1000f, 500f),
            pass = createPassModel(foregroundColor = "rgb(0,0,255)")
        )
    }
}

@Composable
@Preview
fun AztecPreview() {
    CardKeeperTheme {
        BarcodeSection(
            barcode = createBarcode(
                altText = "message",
                format = BarcodeConstants.FORMAT_AZTEC,
                message = "message",
                messageEncoding = "UTF-8",
            ),
            size = Size(1000f, 500f),
            pass = createPassModel(foregroundColor = "rgb(0,0,255)")
        )
    }
}

@Composable
@Preview
fun Code128Preview() {
    CardKeeperTheme {
        BarcodeSection(
            barcode = createBarcode(
                altText = "message",
                format = BarcodeConstants.FORMAT_CODE_128,
                message = "message",
                messageEncoding = "UTF-8",
            ),
            size = Size(1000f, 500f),
            pass = createPassModel(foregroundColor = "rgb(0,0,255)")
        )
    }
}

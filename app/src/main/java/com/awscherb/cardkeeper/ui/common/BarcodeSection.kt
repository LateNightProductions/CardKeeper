package com.awscherb.cardkeeper.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.pkpass.model.isSquare
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.google.zxing.BarcodeFormat

@Composable
fun BarcodeSection(
    barcodeFormat: BarcodeFormat,
    message: String,
    modifier: Modifier = Modifier,
    altText: String? = null,
    altColor: Color = Color.Black,
    altTextIsPreview: Boolean = false,
    backgroundColor: Color? = null
) {

    val width = LocalDensity.current.run {
        LocalConfiguration.current.screenWidthDp.dp.toPx().toInt()
    }

    val height = when (barcodeFormat.isSquare()) {
        true -> width / 2
        false -> width / 6
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        BarcodeImage(
            barcodeFormat = barcodeFormat,
            message = message,
            backgroundColor = backgroundColor,
            modifier = modifier
        )

        if (altText != null) {
            SelectionContainer {
                Text(
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            start = 4.dp,
                            end = 4.dp,
                            bottom = 8.dp
                        )
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    text = altText,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = if (altTextIsPreview) 1 else Int.MAX_VALUE,
                    textAlign = TextAlign.Center,
                    color = altColor,
                )
            }
        } else {
            Spacer(modifier = Modifier.height(8.dp))
        }

    }
}

@Composable
@Preview
fun QrCodePreview() {
    CardKeeperTheme {
        BarcodeSection(
            barcodeFormat = BarcodeFormat.QR_CODE,
            message = "something",
            backgroundColor = Color.Red
        )
    }
}

@Composable
@Preview
fun QrCodeAltPreview() {
    CardKeeperTheme {
        BarcodeSection(
            barcodeFormat = BarcodeFormat.QR_CODE,
            message = "something",
            altText = "something",
            altColor = Color.Green
        )
    }
}

@Composable
@Preview
fun Pdf417Preview() {
    CardKeeperTheme {
        BarcodeSection(
            barcodeFormat = BarcodeFormat.PDF_417,
            message = "something",
            altText = "something",
            altColor = Color.Cyan
        )
    }
}

@Composable
@Preview
fun AztecPreview() {
    CardKeeperTheme {
        BarcodeSection(
            barcodeFormat = BarcodeFormat.AZTEC,
            message = "something",
            altText = "something",
            altColor = Color.Magenta
        )
    }
}

@Composable
@Preview
fun Code128Preview() {
    CardKeeperTheme {
        BarcodeSection(
            barcodeFormat = BarcodeFormat.CODE_128,
            message = "123",
            altText = "something",
            altColor = Color.Yellow
        )
    }
}

@Composable
@Preview
fun TruncateLongAltTExt() {
    CardKeeperTheme {
        BarcodeSection(
            barcodeFormat = BarcodeFormat.PDF_417,
            message = "something",
            altText = "super long text super long text super long text super long text",
            altColor = Color.Cyan,
            altTextIsPreview = true
        )
    }
}
package com.awscherb.cardkeeper.barcode

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.theme.Purple80
import com.awscherb.cardkeeper.compose_common.util.SampleLicense
import com.awscherb.cardkeeper.types.isSquare
import com.awscherb.cardkeeper.types.BarcodeFormat

@Composable
fun BarcodeImage(
    barcodeFormat: BarcodeFormat,
    modifier: Modifier = Modifier,
    message: String,
    backgroundColor: Color? = null
) {

    val width: Int
    val border: Int
    LocalDensity.current.run {
        width = LocalConfiguration.current.screenWidthDp.dp.roundToPx()
        border = 8.dp.roundToPx()
    }

    val height = when (barcodeFormat.isSquare()) {
        true -> width / 2
        false -> width / 6
    }

    val darkMode = isSystemInDarkTheme()
    val bitmap = remember {
        BarcodeEncoder.encodeBitmap(
            contents = message,
            format = barcodeFormat,
            width = (width / 1.5).toInt(),
            height = height,
            inDarkMode = darkMode,
            paddingPx = border
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .align(
                    Alignment.CenterHorizontally
                )
                .padding(
                    start = 8.dp,
                    end = 8.dp,
                )
        ) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentScale = ContentScale.FillWidth,
                contentDescription = "",
                modifier = modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(backgroundColor ?: Color.Transparent)
            )
        }
    }
}

@Composable
@Preview(apiLevel = 33, showSystemUi = true)
fun BarcodeImagePreview() {
    CardKeeperTheme {
        Card {
            BarcodeImage(
                barcodeFormat = BarcodeFormat.QR_CODE,
                message = "something",
                backgroundColor = Purple80
            )
        }
    }
}

@Composable
@Preview(apiLevel = 33, showSystemUi = true)
fun BarcodeImagePDF417Preview() {
    CardKeeperTheme {
        Card {
            BarcodeImage(
                barcodeFormat = BarcodeFormat.PDF_417,
                message = SampleLicense,
            )
        }
    }
}

@Composable
@Preview(apiLevel = 33, showSystemUi = true)
fun BarcodeImageAztecPreview() {
    CardKeeperTheme {
        Card {
            BarcodeImage(
                barcodeFormat = BarcodeFormat.AZTEC,
                message = SampleLicense,
            )
        }
    }
}

@Composable
@Preview(apiLevel = 33, showSystemUi = true)
fun BarcodeImageDataMatrixPreview() {
    CardKeeperTheme {
        Card {
            BarcodeImage(
                barcodeFormat = BarcodeFormat.CODE_128,
                message = SampleLicense,
            )
        }
    }
}
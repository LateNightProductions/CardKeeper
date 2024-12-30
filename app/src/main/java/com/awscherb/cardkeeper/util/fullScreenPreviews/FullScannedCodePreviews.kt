package com.awscherb.cardkeeper.util.fullScreenPreviews

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.awscherb.cardkeeper.ui.scannedCode.ScannedCodeDetail
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.util.createScannedCode
import com.google.zxing.BarcodeFormat

@Composable
@Preview
fun QrCodePreview() {
    CardKeeperTheme {
        ScannedCodeDetail(
            code = createScannedCode(
                text = "Something stored in a QR code"
            ),
            paddingValues = PaddingValues(),
        )
    }
}

@Composable
@Preview
fun CodePreview() {
    CardKeeperTheme {
        ScannedCodeDetail(
            code = createScannedCode(
                format = BarcodeFormat.CODE_128,
                text = "2345234525634243"
            ),
            paddingValues = PaddingValues(),
        )
    }
}
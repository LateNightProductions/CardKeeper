package com.awscherb.cardkeeper.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.awscherb.cardkeeper.ui.card_detail.ScannedCodeDetail
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme

@Composable
@Preview
fun QrCodePreview() {
    CardKeeperTheme {
        ScannedCodeDetail(
            code = createScannedCode(
                text = "Something stored in a QR code"
            ),
            paddingValues = PaddingValues()
        )
    }
}
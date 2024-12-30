package com.awscherb.cardkeeper.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.compose_common.composable.BarcodeImage
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.theme.Typography
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.result.ParsedResultType

@Composable
fun ScannedCodeItem(
    item: ScannedCodeItemModel,
    showBarcode: Boolean = true,
    onClick: (ScannedCodeItemModel) -> Unit,
) {
    ElevatedCard(
        modifier = Modifier.clickable { onClick(item) }
    ) {
        Text(
            text = item.title,
            style = Typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 8.dp)
        )

        if (showBarcode) {
            BarcodeImage(
                barcodeFormat = item.barcodeFormat,
                message = item.message,
            )
        }

        ScannedCodeTextPreview(
            modifier = Modifier.padding(top = 16.dp),
            text = item.message,
            parsedResultType = item.parsedType
        )
    }
}


@Composable
@Preview
fun ScannedCodePreview() {
    CardKeeperTheme {
        ScannedCodeItem(
            item = ScannedCodeItemModel(
                title = "Title",
                message = "message",
                barcodeFormat = BarcodeFormat.QR_CODE,
                parsedType = ParsedResultType.TEXT,
                id = "",
                created = 1
            ),
            onClick = {})
    }
}

@Composable
@Preview
fun ScannedCodeNoBarcodePreview() {
    CardKeeperTheme {
        ScannedCodeItem(
            item = ScannedCodeItemModel(
                title = "Title",
                message = "message",
                barcodeFormat = BarcodeFormat.QR_CODE,
                parsedType = ParsedResultType.TEXT,
                id = "",
                created = 1
            ),
            showBarcode = false,
            onClick = {})
    }
}

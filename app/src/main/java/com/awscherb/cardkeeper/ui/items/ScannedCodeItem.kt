package com.awscherb.cardkeeper.ui.items

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
import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.ui.common.BarcodeImage
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.ui.theme.Typography
import com.awscherb.cardkeeper.util.createScannedCode

@Composable
fun ScannedCodeItem(
    item: ScannedCodeModel,
    showBarcode: Boolean = true,
    onClick: (ScannedCodeModel) -> Unit,
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
                barcodeFormat = item.format,
                message = item.text,
            )
        }

        ScannedCodeTextPreview(
            modifier = Modifier.padding(top = 16.dp),
            text = item.text,
            parsedResultType = item.parsedType
        )
    }
}


@Composable
@Preview
fun ScannedCodePreview() {
    CardKeeperTheme {
        ScannedCodeItem(
            item = createScannedCode(),
            onClick = {})
    }
}

@Composable
@Preview
fun ScannedCodeNoBarcodePreview() {
    CardKeeperTheme {
        ScannedCodeItem(
            item = createScannedCode(),
            showBarcode = false,
            onClick = {})
    }
}

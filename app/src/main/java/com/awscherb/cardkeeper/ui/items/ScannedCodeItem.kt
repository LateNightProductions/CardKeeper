package com.awscherb.cardkeeper.ui.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.ui.common.BarcodeSection
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.ui.theme.Typography
import com.awscherb.cardkeeper.util.EncoderHolder.encoder
import com.awscherb.cardkeeper.util.createScannedCode
import com.google.zxing.BarcodeFormat


@Composable
fun ScannedCodeItem(
    item: ScannedCodeModel,
    size: Size,
    onClick: (ScannedCodeModel) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.clickable { onClick(item) }
    ) {
        Text(
            text = item.title,
            style = Typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        )

        BarcodeSection(
            barcodeFormat = item.format,
            message = item.text,
            size = size,
            altText = item.text
        )
    }
}

@Composable
@Preview
fun ScannedCodePreview() {
    CardKeeperTheme {
        ScannedCodeItem(
            item = createScannedCode(),
            size = Size(500f, 500f),
            onClick = {})
    }
}

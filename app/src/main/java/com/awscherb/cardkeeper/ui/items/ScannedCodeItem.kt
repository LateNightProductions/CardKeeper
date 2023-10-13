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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.ui.theme.Typography
import com.awscherb.cardkeeper.util.EncoderHolder.encoder
import com.awscherb.cardkeeper.util.createScannedCode



@Composable
fun ScannedCodeItem(
    item: ScannedCodeModel,
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
//            modifier = Modifier.constrainAs(description) {
//                linkTo(
//                    top = parent.top, bottom = parent.bottom,
//                    start = image.end, end = headers.start,
//                    startMargin = 8.dp,
//                    endMargin = 8.dp,
//                    horizontalBias = 0f
//                )
//                width = Dimension.fillToConstraints.atMostWrapContent
//            }
        )

//        when (item.format) {
//            BarcodeFormat.QR_CODE, BarcodeFormat.AZTEC, BarcodeFormat.DATA_MATRIX -> codeImage.scaleType = ImageView.ScaleType.FIT_CENTER
//            else -> codeImage.scaleType = ImageView.ScaleType.FIT_XY
//        }

        // Load image
        val bitmap =
            encoder.encodeBitmap(item.text, item.format, 200, 200)

        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "",
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        )


    }

}

@Composable
@Preview
fun ScannedCodePreview() {
    CardKeeperTheme {
        ScannedCodeItem(item = createScannedCode(), onClick = {})
    }
}

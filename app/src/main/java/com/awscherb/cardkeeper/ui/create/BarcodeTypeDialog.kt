package com.awscherb.cardkeeper.ui.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.google.zxing.BarcodeFormat

data class SelectionType(
    val barcodeFormat: BarcodeFormat,
    val label: String
)

private val SelectableTypes = listOf(
    SelectionType(BarcodeFormat.AZTEC, "Aztec"),
    SelectionType(BarcodeFormat.CODE_128, "Code 128"),
    SelectionType(BarcodeFormat.DATA_MATRIX, "Data Matrix"),
    SelectionType(BarcodeFormat.PDF_417, "PDF 417"),
    SelectionType(BarcodeFormat.QR_CODE, "QR Code"),
)

@Composable
fun BarcodeTypeDialog(
    onDismissRequest: () -> Unit,
    onTypeSelected: (SelectionType) -> Unit,
    initialSelectedType: BarcodeFormat? = null
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Choose a barcode type",
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 24.dp,
                        bottom = 16.dp
                    ),
                )

                SelectableTypes.forEach { type ->
                    BarcodeRow(
                        selectionType = type,
                        selected = type.barcodeFormat == initialSelectedType,
                        onTypeSelected = onTypeSelected
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Cancel")
                    }

                }
            }
        }
    }
}

@Composable
fun BarcodeRow(
    selectionType: SelectionType,
    selected: Boolean,
    onTypeSelected: (SelectionType) -> Unit = {}
) {
    Row(
        Modifier
            .clickable {
                onTypeSelected(selectionType)
            }
            .height(36.dp)
            .fillMaxWidth()
    ) {
        if (selected) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .height(24.dp)
                    .width(24.dp)
                    .padding(start = 4.dp),
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = "Selected",
                colorFilter = ColorFilter.tint(Color.Blue)
            )
        }
        Text(
            modifier = Modifier
                .padding(
                    start = if (selected) 8.dp else 32.dp,
                    end = 8.dp
                )
                .align(Alignment.CenterVertically),
            text = selectionType.label,
            color = if (selected) Color.Blue else Color.DarkGray
        )
    }

}

@Composable
@Preview
fun SelectionDialogPreview() {
    CardKeeperTheme {
        BarcodeTypeDialog(onDismissRequest = { }, onTypeSelected = {})
    }
}

@Composable
@Preview
fun SelectionDialogInitialPreview() {
    CardKeeperTheme {
        BarcodeTypeDialog(
            onDismissRequest = { },
            onTypeSelected = {},
            initialSelectedType = BarcodeFormat.PDF_417
        )
    }
}

@Composable
@Preview
fun SelectedBarcodeRowPreview() {
    CardKeeperTheme {
        BarcodeRow(
            selectionType = SelectionType(BarcodeFormat.QR_CODE, "QR Code"),
            selected = true
        )
    }
}

@Composable
@Preview
fun UnselectedBarcodeRowPreview() {
    CardKeeperTheme {
        BarcodeRow(
            selectionType = SelectionType(BarcodeFormat.QR_CODE, "QR Code"),
            selected = false
        )
    }
}
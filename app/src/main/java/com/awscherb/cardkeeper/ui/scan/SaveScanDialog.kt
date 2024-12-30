package com.awscherb.cardkeeper.ui.scan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.util.SampleContact
import com.awscherb.cardkeeper.compose_common.util.SampleLicense
import com.awscherb.cardkeeper.compose_common.util.SampleWifi
import com.awscherb.cardkeeper.ui.common.CodeRichDataSection
import com.awscherb.cardkeeper.util.CapWords
import com.awscherb.cardkeeper.util.GlobalPreviewNightMode
import com.awscherb.cardkeeper.util.ParsedTypeUtils
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.result.ParsedResultType

@Composable
fun SaveScanDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
    data: CreateCodeData
) {
    var title by remember {
        mutableStateOf(ParsedTypeUtils.suggestTitle(data.text, data.parsedResultType))
    }
    Dialog(onDismissRequest = onDismissRequest) {
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

                Box(modifier = Modifier.padding(top = 8.dp))

                CodeRichDataSection(
                    data = data.text,
                    parsedType = data.parsedResultType
                )

                HorizontalDivider(
                    modifier = Modifier.padding(top = 8.dp)
                )

                TextField(
                    value = title,
                    keyboardOptions = CapWords,
                    onValueChange = { title = it },
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    placeholder = { Text(text = "Enter a name") }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Cancel", color = MaterialTheme.colorScheme.error)
                    }
                    TextButton(
                        onClick = { onConfirmation(title) },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Save", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

@Composable
@Preview(apiLevel = 33, uiMode = GlobalPreviewNightMode)
fun SaveScanDialogContactPreview() {
    CardKeeperTheme {
        SaveScanDialog(
            onDismissRequest = { },
            onConfirmation = {},
            data = CreateCodeData(
                BarcodeFormat.QR_CODE, SampleContact, "title", ParsedResultType.ADDRESSBOOK
            )
        )
    }
}

@Composable
@Preview(apiLevel = 33, uiMode = GlobalPreviewNightMode)
fun SaveScanDialogWifiPreview() {
    CardKeeperTheme {
        SaveScanDialog(
            onDismissRequest = { },
            onConfirmation = {},
            data = CreateCodeData(
                BarcodeFormat.QR_CODE, SampleWifi, "title", ParsedResultType.WIFI
            )
        )
    }
}

@Composable
@Preview(apiLevel = 33, uiMode = GlobalPreviewNightMode)
fun SaveScanDialogLicensePreview() {
    CardKeeperTheme {
        SaveScanDialog(
            onDismissRequest = { },
            onConfirmation = {},
            data = CreateCodeData(
                BarcodeFormat.PDF_417, SampleLicense, "", ParsedResultType.TEXT
            )
        )
    }
}

@Composable
@Preview(apiLevel = 33, uiMode = GlobalPreviewNightMode)
fun SaveScanDialogTextPreview() {
    CardKeeperTheme {
        SaveScanDialog(
            onDismissRequest = { },
            onConfirmation = {},
            data = CreateCodeData(
                BarcodeFormat.QR_CODE, "text scanned here", "", ParsedResultType.TEXT
            )
        )
    }
}
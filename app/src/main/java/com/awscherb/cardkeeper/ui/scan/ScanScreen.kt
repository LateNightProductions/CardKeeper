package com.awscherb.cardkeeper.ui.scan

import android.app.Activity
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.awscherb.cardkeeper.ui.common.CodeRichDataSection
import com.awscherb.cardkeeper.ui.common.ScaffoldScreen
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.util.CapWords
import com.awscherb.cardkeeper.util.ParsedTypeUtils
import com.awscherb.cardkeeper.util.SampleContact
import com.awscherb.cardkeeper.util.SampleWifi
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.result.ParsedResultType
import com.google.zxing.client.result.ResultParser
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView
import kotlinx.coroutines.launch

@Composable
fun ScanScreen(
    viewModel: ScanViewModel = hiltViewModel(),
    completion: () -> Unit,
    navOnClick: () -> Unit
) {
    val context = LocalContext.current
    var scanFlag by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val pendingData by viewModel.pendingCreateData.collectAsState(null)
    val createData by viewModel.createResult.collectAsState(initial = false)
    var openDialog by remember { mutableStateOf(false) }

    val compoundBarcodeView = remember {
        CompoundBarcodeView(context).apply {
            val capture = CaptureManager(context as Activity, this)
            capture.initializeFromIntent(context.intent, null)
            this.setStatusText("")
            capture.decode()
            this.decodeContinuous { result ->
                if (scanFlag || openDialog) {
                    return@decodeContinuous
                }
                val res = ResultParser.parseResult(result.result)
                scanFlag = true
                result.text?.let { text ->
                    scope.launch {
                        openDialog = true
                        viewModel.pendingCreateData.value =
                            CreateCodeData(
                                format = result.barcodeFormat,
                                text = text,
                                title = "",
                                parsedResultType = res.type
                            )

                    }

                    scanFlag = false
                }
            }
        }
    }

    LaunchedEffect(createData) {
        if (createData) {
            completion()
        }
    }

    DisposableEffect(key1 = "scan_key") {
        compoundBarcodeView.resume()
        onDispose {
            compoundBarcodeView.pause()
        }
    }

    ScaffoldScreen(title = "Scan", navOnClick = navOnClick) {
        pendingData?.let { pendingData ->
            if (openDialog) {
                SaveScanDialog(
                    data = pendingData,
                    onDismissRequest = { openDialog = false },
                    onConfirmation = { title ->
                        scope.launch {
                            viewModel.createCode(
                                pendingData.copy(
                                    title = title
                                )
                            )

                        }
                    },
                )
            }
        }

        AndroidView(
            modifier = Modifier.padding(it),
            factory = { compoundBarcodeView },
        )
    }
}

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
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = { onConfirmation(title) },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Save", color = Color.Blue)
                    }
                }
            }
        }
    }
}

@Composable
@Preview(apiLevel = 33)
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
@Preview(apiLevel = 33)
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
@Preview(apiLevel = 33)
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
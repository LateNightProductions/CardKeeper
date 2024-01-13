package com.awscherb.cardkeeper.ui.scan

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.awscherb.cardkeeper.ui.common.ScaffoldScreen
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.util.CapWords
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
                    startWith = "",
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
    startWith: String,
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
) {
    var title by remember {
        mutableStateOf(startWith)
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
                Text(
                    text = "Enter a name:",
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 24.dp,
                        bottom = 16.dp
                    ),
                )
                TextField(
                    value = title,
                    keyboardOptions = CapWords,
                    onValueChange = { title = it },
                    modifier = Modifier.padding(horizontal = 16.dp),
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
@Preview
fun SaveScanDialogPreview() {
    CardKeeperTheme {
        SaveScanDialog(
            startWith = "something I scanned but for some reason want a very long name",
            onDismissRequest = { },
            onConfirmation = {}
        )
    }
}
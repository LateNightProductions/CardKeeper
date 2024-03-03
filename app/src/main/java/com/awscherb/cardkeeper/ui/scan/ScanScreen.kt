package com.awscherb.cardkeeper.ui.scan

import android.app.Activity
import android.view.View
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.awscherb.cardkeeper.ui.common.ScaffoldScreen
import com.awscherb.cardkeeper.ui.common.icons.FlashlightOff
import com.awscherb.cardkeeper.ui.common.icons.FlashlightOn
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.util.GlobalPreviewNightMode
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
    val scope = rememberCoroutineScope()

    val pendingData by viewModel.pendingCreateData.collectAsState(null)
    val createData by viewModel.createResult.collectAsState(initial = false)
    var isDialogOpen by remember { mutableStateOf(false) }

    var scanFlag by remember {
        mutableStateOf(false)
    }

    var isFlashOn by remember {
        mutableStateOf(false)
    }

    val compoundBarcodeView = remember {
        CompoundBarcodeView(context).apply {
            val capture = CaptureManager(context as Activity, this)
            capture.initializeFromIntent(context.intent, null)
            this.setStatusText("")
            capture.decode()
            this.decodeContinuous { result ->
                if (scanFlag || isDialogOpen) {
                    return@decodeContinuous
                }
                val res = ResultParser.parseResult(result.result)
                scanFlag = true
                result.text?.let { text ->
                    scope.launch {
                        isDialogOpen = true
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

    DisposableEffect(key1 = "scan_key") {
        compoundBarcodeView.resume()
        onDispose {
            compoundBarcodeView.pause()
        }
    }

    LaunchedEffect(createData) {
        if (createData) {
            completion()
        }
    }

    ScanScreenInner(
        pendingData = pendingData,
        barcodeView = compoundBarcodeView,
        isDialogOpen = isDialogOpen,
        toggleDialogState = { isDialogOpen = !isDialogOpen },
        navOnClick = navOnClick,
        isFlashOn = isFlashOn,
        toggleFlash = {
            compoundBarcodeView.barcodeView.setTorch(!isFlashOn)
            isFlashOn = !isFlashOn
        },
        createCode = { title ->
            scope.launch {
                pendingData?.let {
                    viewModel.createCode(it.copy(title = title))
                }
            }
        }
    )
}

@Composable
fun ScanScreenInner(
    pendingData: CreateCodeData?,
    barcodeView: View,
    isDialogOpen: Boolean,
    isFlashOn: Boolean = false,
    navOnClick: () -> Unit,
    toggleDialogState: () -> Unit,
    toggleFlash: () -> Unit = {},
    createCode: (String) -> Unit
) {

    ScaffoldScreen(
        title = "Scan",
        navOnClick = navOnClick,
        floatingActionButton = {
            FloatingActionButton(onClick = { toggleFlash() }) {
                Icon(
                    imageVector = if (isFlashOn) FlashlightOff else FlashlightOn,
                    contentDescription = "Flash"
                )
            }
        }
    ) {
        pendingData?.let { pendingData ->
            if (isDialogOpen) {
                SaveScanDialog(
                    data = pendingData,
                    onDismissRequest = { toggleDialogState() },
                    onConfirmation = { title ->
                        createCode(title)
                    },
                )
            }
        }

        AndroidView(
            modifier = Modifier.padding(it),
            factory = { barcodeView },
        )

    }
}

@Preview(apiLevel = 33, uiMode = GlobalPreviewNightMode)
@Composable
fun ScanScreenPreview() {
    CardKeeperTheme {
        ScanScreenInner(
            pendingData = null,
            barcodeView = View(LocalContext.current),
            isDialogOpen = false,
            navOnClick = { },
            toggleDialogState = { },
            createCode = {}
        )
    }
}

@Preview(apiLevel = 33, uiMode = GlobalPreviewNightMode)
@Composable
fun ScanScreenFlashOnPreview() {
    CardKeeperTheme {
        ScanScreenInner(
            pendingData = null,
            barcodeView = View(LocalContext.current),
            isDialogOpen = false,
            isFlashOn = true,
            navOnClick = { },
            toggleDialogState = { },
            createCode = {}
        )
    }
}


@Preview(apiLevel = 33, uiMode = GlobalPreviewNightMode)
@Composable
fun ScanScreenTextPreview() {
    CardKeeperTheme {
        ScanScreenInner(
            pendingData = CreateCodeData(
                BarcodeFormat.QR_CODE,
                "here is something you scanned",
                "title",
                ParsedResultType.TEXT
            ),
            barcodeView = View(LocalContext.current),
            isDialogOpen = true,
            navOnClick = { },
            toggleDialogState = { },
            createCode = {}
        )
    }
}

@Preview(apiLevel = 33, uiMode = GlobalPreviewNightMode)
@Composable
fun ScanScreenWifiPreview() {
    CardKeeperTheme {
        ScanScreenInner(
            pendingData = CreateCodeData(
                BarcodeFormat.QR_CODE,
                SampleWifi,
                "title",
                ParsedResultType.WIFI
            ),
            barcodeView = View(LocalContext.current),
            isDialogOpen = true,
            navOnClick = { },
            toggleDialogState = { },
            createCode = {}
        )
    }

}
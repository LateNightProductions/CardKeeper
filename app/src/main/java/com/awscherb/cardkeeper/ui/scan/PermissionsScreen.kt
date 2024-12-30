@file:OptIn(ExperimentalPermissionsApi::class)

package com.awscherb.cardkeeper.ui.scan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.compose_common.theme.ScaffoldScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@Composable
fun PermissionsScreen(
    navOnClick: () -> Unit
) {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    if (cameraPermissionState.status.isGranted) {
        Text("Camera permission Granted")
    } else {
        ScaffoldScreen(title = "Camera", navOnClick = navOnClick) {
            Column(
                Modifier
                    .padding(it)
                    .fillMaxWidth()
            ) {
                val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                    "Camera access is necessary to scan barcodes."
                } else {
                    "Camera not available"
                }

                Text(
                    textToShow, modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(top = 24.dp)
                )
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
                Button(
                    modifier = Modifier.align(CenterHorizontally),
                    onClick = { cameraPermissionState.launchPermissionRequest() }) {
                    Text("Request camera permission")
                }
            }
        }
    }
}


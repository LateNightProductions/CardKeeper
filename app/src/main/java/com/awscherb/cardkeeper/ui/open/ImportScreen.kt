@file:OptIn(ExperimentalPermissionsApi::class)

package com.awscherb.cardkeeper.ui.open

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awscherb.cardkeeper.ui.common.ScaffoldScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@Composable
fun ImportScreen(
    navOnClick: () -> Unit,
    onComplete: () -> Unit,
    viewModel: ImportScreenViewModel = hiltViewModel()
) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { result ->
            result?.let {
                viewModel.startImport(it)

            }
        }
    val result by viewModel.importResult.collectAsState()
    LaunchedEffect(result) {
        if (result is ImportResult.Success) {
            onComplete()
        }
    }
    ScaffoldScreen(title = "Import", navOnClick = navOnClick) {
        Column(
            Modifier
                .padding(it)
                .fillMaxWidth()
        ) {

            Text(
                "Select a file ", modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(top = 24.dp)
            )
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            Button(modifier = Modifier.align(CenterHorizontally),
                onClick = {
                    launcher.launch(
                        arrayOf(
                            "application/x-zip",
                            "application/vnd-com.apple.pkpass",
                            "application/vnd.apple.pkpass"
                        )
                    )
                }) {
                Text("Tap to select a file")
            }
            result?.let {
                if (it is ImportResult.Error) {
                    Text(
                        it.message,
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(top = 24.dp)
                    )
                }
            }
        }
    }
}


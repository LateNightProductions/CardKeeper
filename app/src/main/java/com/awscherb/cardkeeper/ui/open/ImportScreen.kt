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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awscherb.cardkeeper.compose_common.theme.ScaffoldScreen
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.theme.Typography
import com.awscherb.cardkeeper.util.GlobalPreviewNightMode
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@Composable
fun ImportScreen(
    viewModel: ImportScreenViewModel = hiltViewModel(),
    navOnClick: () -> Unit,
    onComplete: () -> Unit,
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

    ImportScreenContent(
        launch = {
            launcher.launch(
                arrayOf(
                    "application/x-zip",
                    "application/vnd-com.apple.pkpass",
                    "application/vnd.apple.pkpass"
                )
            )
        },
        navOnClick = navOnClick,
        errorText = (result as? ImportResult.Error?)?.message
    )
}

@Composable
fun ImportScreenContent(
    launch: () -> Unit,
    navOnClick: () -> Unit,
    errorText: String?
) {
    ScaffoldScreen(title = "Import pass", navOnClick = navOnClick) {
        Column(
            Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            Text(
                "Select a file to import:", modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(top = 24.dp)
            )
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            Button(
                modifier = Modifier.align(CenterHorizontally),
                onClick = {
                    launch()
                }) {
                Text("Open a file...")
            }
            errorText?.let { error ->
                Text(
                    error,
                    color = Color.Red,
                    style = Typography.bodyLarge,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(top = 24.dp)
                )
            }
        }
    }

}

@Preview(uiMode = GlobalPreviewNightMode)
@Composable
fun ImportScreenPreview() {
    CardKeeperTheme {
        ImportScreenContent(
            launch = {},
            navOnClick = {},
            errorText = "Incorrect file type detected"
        )
    }
}

package com.awscherb.cardkeeper.ui.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.theme.ScaffoldScreen
import com.awscherb.cardkeeper.util.CapWords
import com.google.zxing.BarcodeFormat

@Composable
fun CreateScreen(
    viewModel: CreateViewModel = hiltViewModel(),
    completion: () -> Unit,
    navOnClick: () -> Unit
) {
    val createData by viewModel.saveResult.collectAsState(initial = null)

    LaunchedEffect(createData) {
        if (createData is SaveSuccess) {
            completion()
        }
    }

    ScaffoldScreen(title = "Create", navOnClick = navOnClick) {
        CreateScreenInner(padding = it, save = viewModel::save)
    }
}

@Composable
fun CreateScreenInner(
    padding: PaddingValues,
    initialTitle: String = "",
    initialData: String = "",
    startWithDialogOpen: Boolean = false,
    save: (String, String, BarcodeFormat) -> Unit
) {
    var selectedType by remember {
        mutableStateOf<SelectionType?>(null)
    }

    var title by remember {
        mutableStateOf(initialTitle)
    }

    var barcodeData by remember {
        mutableStateOf(initialData)
    }

    var showTypeDialog by remember {
        mutableStateOf(startWithDialogOpen)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
    ) {

        if (showTypeDialog) {
            BarcodeTypeDialog(
                initialSelectedType = selectedType?.barcodeFormat,
                onDismissRequest = {
                    showTypeDialog = false
                }, onTypeSelected = {
                    selectedType = it
                    showTypeDialog = false
                })
        }

        TextField(
            placeholder = {
                Text("Title")
            },
            keyboardOptions = CapWords,
            value = title,
            onValueChange = { title = it },
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 24.dp
                )
                .fillMaxWidth(),
        )

        TextField(
            placeholder = {
                Text("Barcode Data")
            },
            value = barcodeData,
            onValueChange = { barcodeData = it },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                .fillMaxWidth(),
        )

        Button(
            modifier = Modifier
                .padding(
                    top = 24.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .fillMaxWidth(),
            onClick = {
                showTypeDialog = true
            }) {
            Text(
                text = if (selectedType == null) "Select barcode type" else selectedType!!.label,
            )
        }

        Button(
            modifier = Modifier
                .padding(
                    top = 24.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .fillMaxWidth(),
            onClick = {
                selectedType?.barcodeFormat?.let { barcode ->
                    save(title, barcodeData, barcode)
                }
            }) {
            Text(
                text = "Save",
            )
        }
    }
}


@Composable
@Preview(apiLevel = 33)
fun CreateCodeScreenPreview() {
    CardKeeperTheme {
        CreateScreenInner(
            padding = PaddingValues(),
            initialData = "data",
            save = { _, _, _ -> }
        )
    }
}

@Composable
@Preview(apiLevel = 33)
fun CreateCodeScreenDialogPreview() {
    CardKeeperTheme {
        CreateScreenInner(
            padding = PaddingValues(),
            initialTitle = "title",
            initialData = "data",
            startWithDialogOpen = true,
            save = { _, _, _ -> }
        )
    }
}
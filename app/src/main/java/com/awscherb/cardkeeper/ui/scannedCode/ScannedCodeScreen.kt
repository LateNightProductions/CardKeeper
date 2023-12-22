package com.awscherb.cardkeeper.ui.scannedCode

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.ui.common.BarcodeSection
import com.awscherb.cardkeeper.ui.common.ScaffoldScreen
import com.awscherb.cardkeeper.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun ScannedCodeScreen(
    scannedCodeViewModel: ScannedCodeViewModel = hiltViewModel(),
    onDelete: () -> Unit,
    navOnClick: () -> Unit
) {

    val code by scannedCodeViewModel.card.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    var showDeleteMenu by remember {
        mutableStateOf(false)
    }

    ScaffoldScreen(
        title = "Code",
        navIcon = Icons.Default.ArrowBack,
        topBarActions = {
            IconButton(onClick = { showDeleteMenu = true }) {
                Icon(Icons.Default.Delete, "Delete")
            }
        },
        navOnClick = navOnClick
    ) {
        if (showDeleteMenu) {
            AlertDialog(
                icon = {
                    Icon(Icons.Default.Warning, contentDescription = "Warning")
                },
                title = {
                    Text(text = "Delete")
                },
                text = {
                    Text(text = "Delete this item?")
                },
                onDismissRequest = {
                    showDeleteMenu = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            scope.launch {
                                scannedCodeViewModel.delete()
                                onDelete()
                            }
                        }
                    ) {
                        Text("Delete", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDeleteMenu = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )

        }
        code?.let { code ->
            ScannedCodeDetail(
                code = code,
                paddingValues = it,
            )
        }
    }
}

@Composable
fun ScannedCodeDetail(
    code: ScannedCodeModel,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(paddingValues)
            .padding(horizontal = 8.dp, vertical = 8.dp),
    ) {
        SelectionContainer {
            Text(
                text = code.title,
                style = Typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            )
        }

        // Load image
        BarcodeSection(
            barcodeFormat = code.format,
            message = code.text,
            altText = code.text
        )
    }
}
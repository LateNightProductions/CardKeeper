package com.awscherb.cardkeeper.ui.scannedCode

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awscherb.cardkeeper.barcode.entity.ScannedCodeEntity
import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.ui.common.BarcodeImage
import com.awscherb.cardkeeper.ui.common.BarcodeSection
import com.awscherb.cardkeeper.ui.common.CodeRichDataSection
import com.awscherb.cardkeeper.ui.common.ContactView
import com.awscherb.cardkeeper.ui.common.ScaffoldScreen
import com.awscherb.cardkeeper.ui.common.WifiView
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.ui.theme.Typography
import com.awscherb.cardkeeper.util.SampleContact
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.result.ParsedResultType
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
    ElevatedCard(
        modifier = modifier
            .padding(paddingValues)
            .padding(horizontal = 8.dp, vertical = 8.dp),
    ) {
        Box(modifier = Modifier.height(8.dp))

        SelectionContainer {
            Text(
                text = code.title,
                style = Typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            )
        }

        // Load image
        BarcodeImage(
            barcodeFormat = code.format,
            message = code.text,
        )

        CodeRichDataSection(data = code.text, parsedType = code.parsedType)

        Box(modifier = Modifier.height(16.dp))

    }
}

@Composable
@Preview(showSystemUi = true)
fun ScannedCodeContactPreview() {
    CardKeeperTheme {
        ScannedCodeDetail(code = ScannedCodeEntity(
            format = BarcodeFormat.QR_CODE,
            created = 0L,
            text = SampleContact,
            parsedType = ParsedResultType.ADDRESSBOOK,
            title = "Contact"
        ), paddingValues = PaddingValues())
    }
}

@Composable
@Preview(showSystemUi = true)
fun ScannedCodeTextPreview() {
    CardKeeperTheme {
        ScannedCodeDetail(code = ScannedCodeEntity(
            format = BarcodeFormat.QR_CODE,
            created = 0L,
            text = SampleContact,
            parsedType = ParsedResultType.TEXT,
            title = "Contact"
        ), paddingValues = PaddingValues())
    }
}
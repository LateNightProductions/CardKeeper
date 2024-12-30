package com.awscherb.cardkeeper.ui.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.core.SavedItem
import com.awscherb.cardkeeper.pkpass.model.FieldObject
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.util.TransitConstants
import com.awscherb.cardkeeper.compose_common.CardKeeperTheme
import com.awscherb.cardkeeper.util.GlobalPreviewNightMode
import com.awscherb.cardkeeper.util.SampleLicense
import com.awscherb.cardkeeper.util.createBarcode
import com.awscherb.cardkeeper.util.createPassInfo
import com.awscherb.cardkeeper.util.createPassModel
import com.awscherb.cardkeeper.util.createScannedCode
import com.google.zxing.BarcodeFormat

@Composable
fun ItemsList(
    items: List<SavedItem>,
    paddingValues: PaddingValues,
    onClick: (SavedItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            top = 8.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 88.dp
        )
    ) {
        items(
            items,
            key = { it.id },
            contentType = { it::class }
        ) { item ->
            when (item) {
                is ScannedCodeModel -> ScannedCodeItem(
                    item = item,
                ) { onClick(it) }

                is PkPassModel -> PassItem(pass = item) {
                    onClick(it)
                }
            }
        }
    }
}

@Preview(apiLevel = 33, uiMode = GlobalPreviewNightMode, showSystemUi = true)
@Composable
fun ItemsListPreview() {
    CardKeeperTheme {
        ItemsList(
            items = listOf(
                createScannedCode(),
                createPassModel(
                    backgroundColor = "rgb(50,168,96)",
                    foregroundColor = "rgb(255,255,255)",
                    labelColor = "rgb(255,255,255)",
                    logoText = "Flight",
                    barcode = createBarcode(
                        altText = "Alt Text"
                    ),
                    boardingPass = createPassInfo(
                        transitType = TransitConstants.TYPE_AIR,
                        headerFields = listOf(
                            FieldObject("key", "FLIGHT", "DL123"),
                            FieldObject("key", "GATE", "50")
                        ),
                        primaryFields = listOf(
                            FieldObject("origin", "NEW YORK", "JFK"),
                            FieldObject("destination", "LOS ANGELES", "LAX")
                        ),
                        auxiliaryFields = listOf(
                            FieldObject("terminal", "TERMINAL", "4"),
                            FieldObject("gate", "GATE", "50"),
                            FieldObject("seat", "SEAT", "10A"),
                            FieldObject("zone", "ZONE", "1"),
                        ),
                        secondaryFields = listOf(
                            FieldObject("passenger", "PASSENGER", "Joe Android"),
                            FieldObject("boardingTime", "BOARDING", "12:00 PM"),
                        )
                    )
                ),
                createScannedCode(id = 1, title = "Ticket", text = "here's some text"),
                createScannedCode(
                    id = 2,
                    title = "License",
                    text = SampleLicense,
                    format = BarcodeFormat.PDF_417
                ),
            ), paddingValues = PaddingValues(), onClick = {})
    }
}
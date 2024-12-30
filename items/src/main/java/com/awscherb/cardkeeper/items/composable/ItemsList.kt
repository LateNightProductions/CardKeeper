package com.awscherb.cardkeeper.items.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.items.model.ItemModel
import com.awscherb.cardkeeper.items.model.PassItemModel
import com.awscherb.cardkeeper.items.model.ScannedCodeItemModel

@Composable
fun ItemsList(
    items: List<ItemModel>,
    paddingValues: PaddingValues,
    onClick: (ItemModel) -> Unit
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
                is ScannedCodeItemModel -> ScannedCodeItem(
                    item = item,
                ) { onClick(it) }

                is PassItemModel -> PassItem(pass = item) {
                    onClick(it)
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun ItemsListPreview() {
    CardKeeperTheme {
        ItemsList(
            items = listOf(
//                createScannedCode(),
//                createPassModel(
//                    backgroundColor = "rgb(50,168,96)",
//                    foregroundColor = "rgb(255,255,255)",
//                    labelColor = "rgb(255,255,255)",
//                    logoText = "Flight",
//                    barcode = createBarcode(
//                        altText = "Alt Text"
//                    ),
//                    boardingPass = createPassInfo(
//                        transitType = TransitConstants.TYPE_AIR,
//                        headerFields = listOf(
//                            FieldObject("key", "FLIGHT", "DL123"),
//                            FieldObject("key", "GATE", "50")
//                        ),
//                        primaryFields = listOf(
//                            FieldObject("origin", "NEW YORK", "JFK"),
//                            FieldObject("destination", "LOS ANGELES", "LAX")
//                        ),
//                        auxiliaryFields = listOf(
//                            FieldObject("terminal", "TERMINAL", "4"),
//                            FieldObject("gate", "GATE", "50"),
//                            FieldObject("seat", "SEAT", "10A"),
//                            FieldObject("zone", "ZONE", "1"),
//                        ),
//                        secondaryFields = listOf(
//                            FieldObject("passenger", "PASSENGER", "Joe Android"),
//                            FieldObject("boardingTime", "BOARDING", "12:00 PM"),
//                        )
//                    )
//                ),
//                createScannedCode(id = 1, title = "Ticket", text = "here's some text"),
//                createScannedCode(
//                    id = 2,
//                    title = "License",
//                    text = SampleLicense,
//                    format = BarcodeFormat.PDF_417
//                ),
            ), paddingValues = PaddingValues(), onClick = {})
    }
}
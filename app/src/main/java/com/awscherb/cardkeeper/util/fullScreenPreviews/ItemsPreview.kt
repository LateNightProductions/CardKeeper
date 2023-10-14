package com.awscherb.cardkeeper.util.fullScreenPreviews

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.awscherb.cardkeeper.pkpass.model.FieldObject
import com.awscherb.cardkeeper.pkpass.util.TransitConstants
import com.awscherb.cardkeeper.ui.items.ItemsList
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.util.createBarcode
import com.awscherb.cardkeeper.util.createPassInfo
import com.awscherb.cardkeeper.util.createPassModel
import com.awscherb.cardkeeper.util.createScannedCode

@Preview
@Composable
fun ItemsPreview() {
    CardKeeperTheme {
        ItemsList(items = listOf(
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
        ), paddingValues = PaddingValues(), onClick = {})
    }
}
package com.awscherb.cardkeeper.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import com.awscherb.cardkeeper.pkpass.model.FieldObject
import com.awscherb.cardkeeper.pkpass.util.BarcodeConstants
import com.awscherb.cardkeeper.pkpass.util.TransitConstants
import com.awscherb.cardkeeper.ui.pkpassDetail.PassDetail
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme

@Composable
@Preview
fun AirlinePreview() {
    CardKeeperTheme {
        PassDetail(
            size = Size(1000f, 1000f),
            padding = PaddingValues(),
            pass = createPassModel(
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
            )
        )
    }
}

@Composable
@Preview
fun GenericPreview() {
    CardKeeperTheme {
        PassDetail(
            size = Size(1000f, 1000f),
            padding = PaddingValues(),
            pass = createPassModel(
                backgroundColor = "rgb(87,28,220)",
                foregroundColor = "rgb(255,255,255)",
                labelColor = "rgb(255,255,255)",
                barcode = createBarcode(
                    altText = "Alt Text",
                    format = BarcodeConstants.FORMAT_PDF_417
                ),
                boardingPass = createPassInfo(
                    transitType = "other",
                    headerFields = listOf(
                        FieldObject("key", "TRACK", "4")
                    ),
                    primaryFields = listOf(
                        FieldObject("origin", "NYP", "PENN"),
                        FieldObject("destination", "EWR", "EWR")
                    ),
                    auxiliaryFields = listOf(
                        FieldObject("terminal", "TRACK", "4"),
                    ),
                    secondaryFields = listOf(
                        FieldObject("boardingTime", "BOARDING", "12:00 PM"),
                    )
                )
            )
        )
    }
}
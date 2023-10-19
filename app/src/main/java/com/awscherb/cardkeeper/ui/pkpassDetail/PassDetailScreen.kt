package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awscherb.cardkeeper.pkpass.model.FieldObject
import com.awscherb.cardkeeper.pkpass.model.PassInfoType
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.findFirstBarcode
import com.awscherb.cardkeeper.pkpass.model.findPassInfo
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
import com.awscherb.cardkeeper.pkpass.model.passInfoType
import com.awscherb.cardkeeper.pkpass.model.toBarcodeFormat
import com.awscherb.cardkeeper.pkpass.util.BarcodeConstants
import com.awscherb.cardkeeper.ui.common.BarcodeSection
import com.awscherb.cardkeeper.ui.common.PkPassHeaderView
import com.awscherb.cardkeeper.ui.common.ScaffoldScreen
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.util.createBarcode
import com.awscherb.cardkeeper.util.createPassInfo
import com.awscherb.cardkeeper.util.createPassModel

@Composable
fun PassDetailScreen(
    passDetailViewModel: PkPassViewModel = hiltViewModel(),
    navOnClick: () -> Unit
) {
    val pass by passDetailViewModel.pass.collectAsState(initial = null)

    ScaffoldScreen(title = "Pass", navOnClick = navOnClick) {
        pass?.let { pass ->
            PassDetail(
                padding = it,
                pass = pass,
            )
        }
    }
}

@Composable
fun PassDetail(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    pass: PkPassModel,
) {
    Card(
        modifier = modifier
            .padding(padding)
            .padding(horizontal = 8.dp, vertical = 8.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(pass.backgroundColor.parseHexColor())
        )
    ) {
        PkPassHeaderView(
            pass = pass
        )

        pass.findPassInfo()?.let { passInfo ->
            when (pass.passInfoType) {
                PassInfoType.BOARDING_PASS -> BoardingPass(pass, passInfo)
                PassInfoType.STORE_CARD -> StoreCard(pass, passInfo)
                PassInfoType.GENERIC -> {}
                PassInfoType.EVENT_TICKET -> {}
                null -> {}
            }
        }

        pass.findFirstBarcode()?.let { barcode ->
            BarcodeSection(
                modifier = Modifier.padding(
                    top = if (pass.footerPath != null) 0.dp else 16.dp
                ),
                message = barcode.message,
                barcodeFormat = barcode.format.toBarcodeFormat(),
                altText = barcode.altText,
                altColor = Color(pass.foregroundColor.parseHexColor())
            )
        }
    }
}

@Composable
@Preview
fun GenericPreview() {
    CardKeeperTheme {
        PassDetail(
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


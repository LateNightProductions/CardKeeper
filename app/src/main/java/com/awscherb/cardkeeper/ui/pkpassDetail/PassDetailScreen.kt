package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import com.awscherb.cardkeeper.pkpass.model.FieldObject
import com.awscherb.cardkeeper.pkpass.model.PassInfoType
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.findFirstBarcode
import com.awscherb.cardkeeper.pkpass.model.findPassInfo
import com.awscherb.cardkeeper.pkpass.model.isTransit
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
import com.awscherb.cardkeeper.pkpass.model.passInfoType
import com.awscherb.cardkeeper.pkpass.model.toBarcodeFormat
import com.awscherb.cardkeeper.pkpass.util.BarcodeConstants
import com.awscherb.cardkeeper.ui.common.BarcodeSection
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.ui.view.PkPassHeaderView
import com.awscherb.cardkeeper.ui.view.ScaffoldScreen
import com.awscherb.cardkeeper.util.createBarcode
import com.awscherb.cardkeeper.util.createPassInfo
import com.awscherb.cardkeeper.util.createPassModel

@Composable
fun PassDetailScreen(
    passDetailViewModel: PkPassViewModel = hiltViewModel(),
    navOnClick: () -> Unit
) {
    val pass by passDetailViewModel.pass.collectAsState(initial = null)

    var size by remember {
        mutableStateOf(Size.Zero)
    }

    ScaffoldScreen(title = "Pass", navOnClick = navOnClick) {
        pass?.let { pass ->
            PassDetail(
                modifier = Modifier.onGloballyPositioned {
                    size = it.size.toSize()
                },
                padding = it,
                pass = pass,
                size = size
            )
        }
    }
}

@Composable
fun PassDetail(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    pass: PkPassModel,
    size: Size,
) {
    Box {
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
                    size = size,
                    altColor = Color(pass.foregroundColor.parseHexColor())
                )
            }
        }
        Box(
            modifier = Modifier
                .rotate(-45f)
                .absoluteOffset(x = -170.dp, y = -70.dp)
        ) {
                Box(
                    modifier = Modifier
                        .size(width = 2000.dp, height=50.dp)
                        .clip(RectangleShape)
                        .background(Color.Red)
                )

            Text(
                text = "BETA",
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

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


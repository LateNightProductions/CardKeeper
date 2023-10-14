package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.findFirstBarcode
import com.awscherb.cardkeeper.pkpass.model.findPassInfo
import com.awscherb.cardkeeper.pkpass.model.isTransit
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
import com.awscherb.cardkeeper.pkpass.model.toBarcodeFormat
import com.awscherb.cardkeeper.ui.common.BarcodeSection
import com.awscherb.cardkeeper.ui.view.PkPassHeaderView
import com.awscherb.cardkeeper.ui.view.ScaffoldScreen


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
            if (passInfo.isTransit()) {
                BoardingPass(pass = pass, passInfo = passInfo)
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
}

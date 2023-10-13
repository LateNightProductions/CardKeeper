package com.awscherb.cardkeeper.ui.pkpassDetail

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.awscherb.cardkeeper.pkpass.model.findPassInfo
import com.awscherb.cardkeeper.pkpass.model.isTransit
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
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
            Card(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .onGloballyPositioned {
                        size = it.size.toSize()
                    },

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

                pass.barcode?.let { barcode ->
                    BarcodeSection(pass = pass, barcode = barcode, size = size)
                }
            }
        }
    }
}


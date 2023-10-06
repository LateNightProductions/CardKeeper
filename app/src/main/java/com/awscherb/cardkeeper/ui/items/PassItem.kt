package com.awscherb.cardkeeper.ui.items

import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.awscherb.cardkeeper.pkpass.model.FieldObject
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.ui.view.PkPassHeaderView
import com.awscherb.cardkeeper.util.createPassInfo
import com.awscherb.cardkeeper.util.createPassModel

@Composable
fun PassItem(
    pass: PkPassModel,
    onClick: (PkPassModel) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.clickable { onClick(pass) },
        colors = CardDefaults.cardColors(
            containerColor = Color(pass.backgroundColor.parseHexColor())
        )
    ) {
        PkPassHeaderView(pass = pass)
    }
}

@Composable
@Preview
fun PassItemPreview() {
    CardKeeperTheme {
        PassItem(
            onClick = {},
            pass = createPassModel(
                backgroundColor = "rgb(255,128,0)",
                logoText = "Loyalty Card",
                boardingPass = createPassInfo(
                    headerFields = listOf(
                        FieldObject("key", "label", "value")
                    )
                )
            )
        )
    }
}
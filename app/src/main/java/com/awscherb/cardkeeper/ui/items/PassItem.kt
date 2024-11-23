package com.awscherb.cardkeeper.ui.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.awscherb.cardkeeper.pkpass.model.FieldObject
import com.awscherb.cardkeeper.pkpass.model.PassInfoType
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
import com.awscherb.cardkeeper.pkpass.model.passInfoType
import com.awscherb.cardkeeper.ui.common.PkPassHeaderView
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.util.SampleEvent
import com.awscherb.cardkeeper.util.createPassInfo
import com.awscherb.cardkeeper.util.createPassModel

@Composable
fun PassItem(
    pass: PkPassModel,
    onClick: (PkPassModel) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .clickable { onClick(pass) }
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
            .drawWithContent {
                drawContent()
                if (pass.passInfoType == PassInfoType.EVENT_TICKET) {
                    drawCircle(
                        color = Color.Red,
                        radius = 32.dp.toPx(),
                        center = Offset(
                            x = this.center.x,
                            y = -(16.dp.toPx())
                        ),
                        blendMode = BlendMode.DstOut
                    )
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = Color(pass.backgroundColor.parseHexColor())
        )
    ) {
        Box {
            pass.backgroundPath?.let { background ->
                AsyncImage(
                    model = background,
                    contentDescription = "Background",
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .blur(radius = 32.dp)
                )
            }
            PkPassHeaderView(pass = pass)
        }
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

@Composable
@Preview
fun PassItemEventPreview() {
    CardKeeperTheme {
        PassItem(
            onClick = {},
            pass = SampleEvent
        )

    }
}
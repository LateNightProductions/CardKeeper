package com.awscherb.cardkeeper.items.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
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
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.items.model.PassItemModel
import com.awscherb.cardkeeper.passUi.PassHeaderModel
import com.awscherb.cardkeeper.passUi.PkPassHeaderView

@Composable
fun PassItem(
    pass: PassItemModel,
    onClick: (PassItemModel) -> Unit
) {
    Box(
        modifier = Modifier
            .clickable { onClick(pass) }
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
            .drawWithContent {
                drawContent()
                if (pass.isEvent) {
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
            }
            .clip(RoundedCornerShape(if (pass.isEvent) 0.dp else 8.dp))
            .background(color = Color(pass.backgroundColor))

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
            PkPassHeaderView(
                pass = pass.header
            )
        }
    }
}

@Composable
@Preview
fun PassItemPreview() {
    CardKeeperTheme {
        PassItem(
            onClick = {},
            pass = PassItemModel(
                backgroundColor = 0xff0000,
                id = "",
                header = PassHeaderModel(
                    logo = "",
                    description = "desc",
                    foregroundColor = 0xff0000,
                    labelColor = 0xff0000,
                    logoText = "logo",
                    headerConfig = emptyList(),
                ),
                created = 1
            )
        )
    }
}
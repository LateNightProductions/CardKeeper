package com.awscherb.cardkeeper.items.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.items.model.GroupedPassItemModel
import com.awscherb.cardkeeper.items.model.PassItemModel
import com.awscherb.cardkeeper.passUi.FieldConfig
import com.awscherb.cardkeeper.passUi.PassHeaderModel

@Composable
fun GroupedPassItem(
    group: GroupedPassItemModel,
    modifier: Modifier = Modifier,
    onClick: (GroupedPassItemModel) -> Unit
) {
    Box(modifier = modifier) {
        if (group.passes.size > 2) {
            PassItem(
                pass = group.passes[2],
                modifier = Modifier
                    .offset(x = 8.dp, y = (-8).dp)
                    .alpha(0.4f)
            ) {}
        }
        if (group.passes.size > 1) {
            PassItem(
                pass = group.passes[1],
                modifier = Modifier
                    .offset(x = 4.dp, y = (-4).dp)
                    .alpha(0.7f)
            ) {}
        }
        PassItem(
            pass = group.passes[0]
        ) { onClick(group) }
    }
}

private fun sampleBoardingPass(id: String, name: String, seat: String) = PassItemModel(
    id = id,
    backgroundColor = Color(0xFF1A3A5C),
    header = PassHeaderModel(
        logo = null,
        logoText = "SkyWave Air",
        description = "SFO → JFK",
        foregroundColor = Color.White,
        labelColor = Color(0xFFAACCEE),
        headerConfig = listOf(
            FieldConfig(label = "Flight", value = "SW 412", labelColor = Color(0xFFAACCEE), valueColor = Color.White),
            FieldConfig(label = "Seat", value = seat, labelColor = Color(0xFFAACCEE), valueColor = Color.White),
        )
    ),
    created = 0L,
    sortOrder = 0L
)

@Preview
@Composable
fun GroupedPassItemPreview() {
    CardKeeperTheme {
        GroupedPassItem(
            group = GroupedPassItemModel(
                id = "skywave|2026-06-15T08:30-07:00",
                passes = listOf(
                    sampleBoardingPass("sw-412-1", "John Doe", "12A"),
                    sampleBoardingPass("sw-412-2", "Jane Doe", "12B"),
                ),
                created = 0L,
                sortOrder = 0L
            ),
            onClick = {}
        )
    }
}

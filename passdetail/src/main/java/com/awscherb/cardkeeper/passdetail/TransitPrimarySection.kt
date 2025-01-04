package com.awscherb.cardkeeper.passdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atMostWrapContent
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.theme.Typography

@Composable
fun TransitPrimarySection(
    modifier: Modifier = Modifier,
    fromAirport: String,
    fromCode: String,
    toAirport: String,
    toCode: String,
    tint: Color,
    transitType: TransitModel.Type
) {
    Column(modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = fromAirport.uppercase(),
                color = tint,
                style = Typography.labelMedium
            )
            Text(
                text = toAirport.uppercase(),
                color = tint,
                style = Typography.labelMedium
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            ConstraintLayout(
                modifier =
                    Modifier.fillMaxWidth()
            ) {
                val (from, image, to) = createRefs()

                Text(
                    text = fromCode,
                    color = tint,
                    style = Typography.displayMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.constrainAs(from) {
                        linkTo(start = parent.start, end = image.start, bias = 0f)
                        width = Dimension.fillToConstraints.atMostWrapContent
                    }
                )

                val transitIcon = when (transitType) {
                    TransitModel.Type.AIR -> R.drawable.ic_airplane
                    TransitModel.Type.BOAT -> R.drawable.ic_boat
                    TransitModel.Type.BUS -> R.drawable.ic_bus
                    TransitModel.Type.GENERIC -> R.drawable.ic_up
                    TransitModel.Type.TRAIN -> R.drawable.ic_train
                }

                val rotation = if (transitType == TransitModel.Type.AIR ||
                    transitType == TransitModel.Type.GENERIC
                ) 90f else 0f

                Image(
                    modifier = Modifier
                        .size(42.dp)
                        .rotate(rotation)
                        .constrainAs(image) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    painter = painterResource(id = transitIcon),
                    contentDescription = "Pass",
                    colorFilter = ColorFilter.tint(tint)
                )

                Text(
                    modifier = Modifier
                        .constrainAs(to) {
                            linkTo(start = image.end, end = parent.end, bias = 1f)
                            width = Dimension.fillToConstraints.atMostWrapContent
                        },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = toCode,
                    color = tint,
                    style = Typography.displayMedium

                )
            }

        }

    }
}

@Composable
@Preview
fun TransitPrimaryAir() {
    CardKeeperTheme {
        TransitPrimarySection(
            modifier = Modifier,
            "Newark-Liberty Intl",
            "EWR",
            "Ithaca",
            "ITH",
            Color.Cyan,
            transitType = TransitModel.Type.AIR
        )
    }
}

@Composable
@Preview
fun TransitPrimaryAirTruncated() {
    CardKeeperTheme {
        TransitPrimarySection(
            modifier = Modifier,
            "Newark-Liberty Intl",
            "EWR LONG NAME",
            "Ithaca",
            "ITH REALLY LONG NAME",
            Color.Magenta,
            transitType = TransitModel.Type.AIR
        )
    }
}

@Composable
@Preview
fun TransitPrimaryTrain() {
    CardKeeperTheme {
        TransitPrimarySection(
            modifier = Modifier,
            "New York Penn",
            "PENN",
            "Boston South Station",
            "BOS",
            Color.Yellow,
            transitType = TransitModel.Type.TRAIN
        )
    }
}

@Composable
@Preview
fun TransitPrimaryBoat() {
    CardKeeperTheme {
        TransitPrimarySection(
            modifier = Modifier,
            "Southampton",
            "STH",
            "New York",
            "NYC",
            Color.Green,
            transitType = TransitModel.Type.BOAT
        )
    }
}

@Composable
@Preview
fun TransitPrimaryBus() {
    CardKeeperTheme {
        TransitPrimarySection(
            modifier = Modifier,
            "Boston",
            "BOS",
            "New York",
            "NYC",
            Color.Blue,
            transitType = TransitModel.Type.BUS
        )
    }
}

@Composable
@Preview
fun TransitPrimaryGeneric() {
    CardKeeperTheme {
        TransitPrimarySection(
            modifier = Modifier,
            "Somewhere",
            "SOM",
            "Nowhere",
            "NOW",
            Color.Red,
            transitType = TransitModel.Type.GENERIC
        )
    }
}
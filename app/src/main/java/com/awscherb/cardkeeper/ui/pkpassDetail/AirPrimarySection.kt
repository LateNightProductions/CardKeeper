package com.awscherb.cardkeeper.ui.pkpassDetail

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
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.ui.theme.Typography
import android.graphics.Color as GraphicsColor


@Composable
fun AirPrimarySection(
    modifier: Modifier = Modifier,
    fromAirport: String,
    fromCode: String,
    toAirport: String,
    toCode: String,
    tint: Int
) {
    val tintColor = Color(tint)
    Column(modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = fromAirport.uppercase(),
                color = tintColor,
                style = Typography.labelMedium
            )
            Text(
                text = toAirport.uppercase(),
                color = tintColor,
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
                    color = tintColor,
                    style = Typography.displayMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.constrainAs(from) {
                        linkTo(start = parent.start, end = image.start, bias = 0f)
                        width = Dimension.fillToConstraints.atMostWrapContent
                    }
                )

                Image(
                    modifier = Modifier
                        .size(42.dp)
                        .rotate(90f)
                        .constrainAs(image) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    painter = painterResource(id = R.drawable.ic_airplane),
                    contentDescription = "Pass",
                    colorFilter = ColorFilter.tint(tintColor)
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
                    color = tintColor,
                    style = Typography.displayMedium

                )
            }

        }

    }
}

@Composable
@Preview(showSystemUi = true)
fun AirPrimaryPreview() {
    CardKeeperTheme {
        AirPrimarySection(
            modifier = Modifier,
            "Newark-Liberty Intl",
            "EWR",
            "Ithaca",
            "ITH",
            GraphicsColor.parseColor("#00aa00")
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun AirPrimaryTruncateName() {
    CardKeeperTheme {
        AirPrimarySection(
            modifier = Modifier,
            "Newark-Liberty Intl",
            "EWR LONG NAME",
            "Ithaca",
            "ITH REALLY LONG NAME",
            GraphicsColor.parseColor("#00aa00")
        )
    }
}
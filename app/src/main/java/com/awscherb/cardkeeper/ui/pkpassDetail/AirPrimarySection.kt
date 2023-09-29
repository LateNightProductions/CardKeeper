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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.ui.theme.Typography
import android.graphics.Color as GraphicsColor


@Composable
fun AirPrimarySection(
    fromAirport: String,
    fromCode: String,
    toAirport: String,
    toCode: String,
    tint: Int
) {
    val tintColor = Color(tint)
    Column {
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
            ConstraintLayout(modifier =
            Modifier.fillMaxWidth()) {
                val (from, image, to) = createRefs()

                Text(
                    text = fromCode,
                    color = tintColor,
                    style = Typography.displayMedium,
                    modifier = Modifier.constrainAs(from) {
                        start.linkTo(parent.start)
                    }
                )

                Image(
                    modifier = Modifier
                        .size(42.dp)
                        .constrainAs(image) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    painter = painterResource(id = R.drawable.ic_airplane),
                    contentDescription = "Pass"
                )

                Text(
                    modifier = Modifier.constrainAs(to) {
                        end.linkTo(parent.end)
                    },
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
            "Newark-Liberty Intl",
            "EWR",
            "Ithaca",
            "ITH",
            GraphicsColor.parseColor("#FFFFFF")
        )
    }
}
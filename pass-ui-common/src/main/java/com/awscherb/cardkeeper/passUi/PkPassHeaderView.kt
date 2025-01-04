package com.awscherb.cardkeeper.passUi

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atMostWrapContent
import coil.compose.AsyncImage
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.theme.Typography
import com.awscherb.cardkeeper.pass_ui_common.R


@Composable
fun PkPassHeaderView(
    modifier: Modifier = Modifier,
    pass: PassHeaderModel,
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        val (image, description, headers) = createRefs()

        AsyncImage(
            model = pass.logo,
            placeholder = painterResource(id = R.drawable.logo_placeholder),
            fallback = painterResource(id = R.drawable.logo_placeholder),
            contentDescription = pass.description,
            modifier = Modifier
                .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
                .wrapContentHeight()
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = pass.logoText ?: "",
            style = Typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(description) {
                linkTo(
                    top = parent.top, bottom = parent.bottom,
                    start = image.end, end = headers.start,
                    startMargin = 8.dp,
                    endMargin = 8.dp,
                    horizontalBias = 0f
                )
                width = Dimension.fillToConstraints.atMostWrapContent
            },
            color = pass.foregroundColor
        )

        Row(
            modifier = Modifier.constrainAs(headers) {
                linkTo(
                    top = parent.top, bottom = parent.bottom,
                    start = headers.end, end = parent.end,
                    endMargin = 8.dp,
                    horizontalBias = 1f
                )
            }
        ) {
            pass.headerConfig.let { passHeaders ->
                if (passHeaders.isEmpty()) {
                    return@let
                }

                val firstPass = passHeaders[0]
                FieldTextView(
                    fieldConfig = firstPass
                )
                if (passHeaders.size > 1) {
                    val secondPass = passHeaders[1]
                    FieldTextView(
                        modifier = Modifier.padding(start = 8.dp),
                        fieldConfig = secondPass
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PassHeaderTextSingleHeader() {
    CardKeeperTheme {
        PkPassHeaderView(
            pass = PassHeaderModel(
                logoText = "Loyalty Card",
                logo = "",
                foregroundColor = Color.Red,
                labelColor = Color.White,
                description = "Something",
                headerConfig = listOf(
                    FieldConfig(
                        label = "label",
                        value = "value",
                        labelColor = 0xFF0000
                    ),
                )
            ),
        )
    }
}

@Composable
@Preview
fun PassHeaderMultipleHeaders() {
    CardKeeperTheme {
        PkPassHeaderView(
            pass = PassHeaderModel(
                logoText = "Loyalty Card",
                logo = "",
                foregroundColor = Color.Red,
                labelColor = Color.Red,
                description = "Something",
                headerConfig = listOf(
                    FieldConfig(
                        label = "label",
                        value = "value",
                        labelColor = 0xFF0000
                    ),
                    FieldConfig(
                        label = "label",
                        value = "value",
                        labelColor = 0xFF0000
                    ),
                )
            ),
        )
    }
}

@Composable
@Preview
fun PassHeaderLongNameNoHeaders() {
    CardKeeperTheme {
        PkPassHeaderView(
            pass = PassHeaderModel(
                logoText = "Loyalty Card name with a really long name",
                logo = "",
                foregroundColor = Color.Red,
                labelColor = Color.Red,
                description = "Something",
                headerConfig = listOf(
                )
            ),
        )
    }
}

@Composable
@Preview
fun PassHeaderLongTextMultipleHeaders() {
    CardKeeperTheme {
        PkPassHeaderView(
            pass = PassHeaderModel(
                logoText = "Loyalty Card header with a long name",
                logo = "",
                foregroundColor = Color.Red,
                labelColor = Color.Red,
                description = "Something",
                headerConfig = listOf(
                    FieldConfig(
                        label = "label",
                        value = "value",
                        labelColor = 0xFF0000
                    ),
                    FieldConfig(
                        label = "label",
                        value = "value",
                        labelColor = 0xFF0000
                    ),
                )
            ),
        )
    }
}

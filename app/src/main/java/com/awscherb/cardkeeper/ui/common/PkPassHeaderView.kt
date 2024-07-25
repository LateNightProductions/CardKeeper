package com.awscherb.cardkeeper.ui.common

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
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.pkpass.model.FieldObject
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.findPassInfo
import com.awscherb.cardkeeper.pkpass.model.getTranslatedLabel
import com.awscherb.cardkeeper.pkpass.model.getTranslatedValue
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
import com.awscherb.cardkeeper.ui.pkpassDetail.FieldTextView
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.ui.theme.Typography
import com.awscherb.cardkeeper.util.createPassInfo
import com.awscherb.cardkeeper.util.createPassModel


@Composable
fun PkPassHeaderView(
    modifier: Modifier = Modifier,
    pass: PkPassModel,
    showPlaceholder: Boolean = false
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        val (image, description, headers) = createRefs()

        AsyncImage(
            model = pass.logoPath,
            placeholder = painterResource(id = R.drawable.logo_placeholder),
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
            color = Color(pass.foregroundColor.parseHexColor())
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
            pass.findPassInfo()?.headerFields?.let { passHeaders ->
                if (passHeaders.isEmpty()) {
                    return@let
                }

                val labelColor = pass.labelColor.parseHexColor()
                val valueColor = pass.foregroundColor.parseHexColor()

                val firstPass = passHeaders[0]
                FieldTextView(
                    fieldConfig = FieldConfig(
                        label = pass.getTranslatedLabel(firstPass.label),
                        value = pass.getTranslatedValue(firstPass.typedValue),
                        labelColor = labelColor,
                        valueColor = valueColor
                    )
                )
                if (passHeaders.size > 1) {
                    val secondPass = passHeaders[1]
                    FieldTextView(
                        modifier = Modifier.padding(start = 8.dp),
                        fieldConfig = FieldConfig(
                            label = pass.getTranslatedLabel(secondPass.label),
                            value = pass.getTranslatedValue(secondPass.typedValue),
                            labelColor = labelColor,
                            valueColor = valueColor
                        )
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
            pass = createPassModel(
                logoText = "Loyalty Card",
                boardingPass = createPassInfo(
                    headerFields = listOf(
                        FieldObject("key", "label", "value")
                    )
                ),
            ),
            showPlaceholder = true
        )
    }
}

@Composable
@Preview
fun PassHeaderMultipleHeaders() {
    CardKeeperTheme {
        PkPassHeaderView(
            pass = createPassModel(
                boardingPass = createPassInfo(
                    headerFields = listOf(
                        FieldObject("key", "label", "value"),
                        FieldObject("key", "label", "value")
                    )
                ),
            ),
            showPlaceholder = true
        )
    }
}

@Composable
@Preview
fun PassHeaderLongNameNoHeaders() {
    CardKeeperTheme {
        PkPassHeaderView(
            pass = createPassModel(
                logoText = "Header Field With A Super Long Name Should Be Ellipsized",
                boardingPass = createPassInfo()
            ),
            showPlaceholder = true
        )
    }
}

@Composable
@Preview
fun PassHeaderLongTextMultipleHeaders() {
    CardKeeperTheme {
        PkPassHeaderView(
            pass = createPassModel(
                logoText = "Header Field With A Super Long Name Should Be Ellipsized",
                boardingPass = createPassInfo(
                    headerFields = listOf(
                        FieldObject("key", "label", "value"),
                        FieldObject("key", "label", "value")
                    )
                )
            ),
            showPlaceholder = true
        )
    }
}

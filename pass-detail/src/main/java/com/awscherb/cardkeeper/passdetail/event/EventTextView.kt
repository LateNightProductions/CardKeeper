package com.awscherb.cardkeeper.passdetail.event

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.theme.Typography
import com.awscherb.cardkeeper.passUi.FieldConfig
import com.awscherb.cardkeeper.pkpass.model.parseHexColor

@Composable
fun EventTextView(
    modifier: Modifier = Modifier,
    primary: FieldConfig,
    alignment: Alignment.Horizontal = Alignment.Start
) {
    EventTextViewInner(
        modifier = modifier,
        fieldConfig = primary,
        alignment = alignment
    )
}


@Composable
fun EventTextViewInner(
    modifier: Modifier = Modifier,
    fieldConfig: FieldConfig,
    alignment: Alignment.Horizontal = Alignment.Start
) {
    Column(modifier = modifier) {
        fieldConfig.label?.let { label ->
            Text(
                text = label,
                color = fieldConfig.labelColor,
                style = Typography.bodyMedium,
                modifier = Modifier.align(alignment)
            )
        }
        Text(
            text = fieldConfig.value,
            modifier = Modifier.align(alignment),
            color = fieldConfig.valueColor,
            style = Typography.titleLarge
        )

    }
}

@Composable
@Preview
fun EventTextViewPreview() {
    CardKeeperTheme {
        EventTextViewInner(
            fieldConfig = FieldConfig(
                label = "Time",
                value = "12:00",
                labelColor = Color("rgb(255,120,65)".parseHexColor())
            )
        )
    }
}


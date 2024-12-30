package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.awscherb.cardkeeper.compose_common.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.Typography
import com.awscherb.cardkeeper.passUi.FieldConfig
import com.awscherb.cardkeeper.pkpass.model.FieldObject
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.getTranslatedLabel
import com.awscherb.cardkeeper.pkpass.model.getTranslatedValue
import com.awscherb.cardkeeper.pkpass.model.parseHexColor

@Composable
fun EventTextView(
    modifier: Modifier = Modifier,
    pass: PkPassModel,
    primary: FieldObject,
    alignment: Alignment.Horizontal = Alignment.Start
) {
    EventTextViewInner(
        modifier = modifier,
        fieldConfig = FieldConfig(
            label = pass.getTranslatedLabel(primary.label),
            value = pass.getTranslatedValue(primary.value),
            labelColor = pass.labelColor.parseHexColor(),
            valueColor = pass.foregroundColor.parseHexColor()
        ),
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
                color = Color(fieldConfig.labelColor),
                style = Typography.bodyMedium,
                modifier = Modifier.align(alignment)
            )
        }
        Text(
            text = fieldConfig.value,
            modifier = Modifier.align(alignment),
            color = Color(fieldConfig.valueColor),
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
                labelColor = "rgb(255,120,65)".parseHexColor()
            )
        )
    }
}


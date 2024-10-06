package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import com.awscherb.cardkeeper.pkpass.model.FieldObject
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.getTranslatedLabel
import com.awscherb.cardkeeper.pkpass.model.getTranslatedValue
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.ui.theme.Typography
import com.awscherb.cardkeeper.ui.common.FieldConfig

@Composable
fun PrimaryTextView(
    modifier: Modifier = Modifier,
    pass: PkPassModel,
    primary: FieldObject,
    alignment: Alignment.Horizontal = Alignment.Start
) {
    PrimaryTextViewInner(
        modifier = modifier,
        fieldConfig = FieldConfig(
            label = pass.getTranslatedLabel(primary.label),
            value = pass.getTranslatedValue(primary.typedValue),
            labelColor = pass.foregroundColor.parseHexColor(),
            valueColor = pass.foregroundColor.parseHexColor()
        ),
        alignment = alignment
    )
}


@Composable
fun PrimaryTextViewInner(
    modifier: Modifier = Modifier,
    fieldConfig: FieldConfig,
    alignment: Alignment.Horizontal = Alignment.Start
) {
    Column(modifier = modifier) {
        Text(
            text = fieldConfig.value,
            modifier = Modifier.align(alignment),
            color = Color(fieldConfig.valueColor),
            style = Typography.displaySmall
        )
        if (fieldConfig.label != null) {
            Text(
                text = fieldConfig.label,
                color = Color(fieldConfig.labelColor),
                style = Typography.bodyLarge,
                modifier = Modifier.align(alignment)
            )
        }
    }
}

@Composable
@Preview
fun PrimaryTextViewPreview() {
    CardKeeperTheme {
        PrimaryTextViewInner(
            fieldConfig = FieldConfig(
                label = "Time",
                value = "12:00",
                labelColor = "rgb(255,120,65)".parseHexColor()
            )
        )
    }
}


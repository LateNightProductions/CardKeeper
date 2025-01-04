package com.awscherb.cardkeeper.passdetail

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
fun PrimaryTextView(
    modifier: Modifier = Modifier,
    pass: PassDetailModel,
    primary: FieldConfig,
    alignment: Alignment.Horizontal = Alignment.Start
) {
    PrimaryTextViewInner(
        modifier = modifier,
        fieldConfig = primary
//            FieldConfig(
//            label = pass.getTranslatedLabel(primary.label),
//            value = pass.getTranslatedValue(primary.typedValue),
//            labelColor = pass.foregroundColor.parseHexColor(),
//            valueColor = pass.foregroundColor.parseHexColor()
//        )
        ,
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
        fieldConfig.label?.let { label ->
            Text(
                text = label,
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


package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.ui.theme.Typography
import com.awscherb.cardkeeper.ui.view.FieldConfig
import com.awscherb.cardkeeper.ui.view.PrimaryFieldView

@Composable
fun PrimaryTextView(
    modifier: Modifier = Modifier,
    fieldConfig: FieldConfig,
    alignment: Alignment.Horizontal = Alignment.Start
) {
    Column(modifier = modifier) {
        Text(
            text = fieldConfig.value,
            modifier = Modifier.align(alignment),
            color = Color(fieldConfig.valueColor),
            style = Typography.displayLarge
        )
        if (fieldConfig.label != null) {
            Text(
                text = fieldConfig.label.uppercase(),
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
        PrimaryTextView(
            fieldConfig = FieldConfig(
                label = "time",
                value = "12:00",
                labelColor = "rgb(0,0,0)".parseHexColor()
            )
        )
    }
}


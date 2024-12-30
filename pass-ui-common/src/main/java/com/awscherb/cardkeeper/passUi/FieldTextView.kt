package com.awscherb.cardkeeper.passUi

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.awscherb.cardkeeper.compose_common.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.Typography

@Composable
fun FieldTextView(
    modifier: Modifier = Modifier,
    fieldConfig: FieldConfig,
    alignment: Alignment.Horizontal = Alignment.End
) {
    Column(modifier = modifier) {
        if (fieldConfig.label != null) {
            Text(
                text = fieldConfig.label.uppercase(),
                color = Color(fieldConfig.labelColor),
                style = Typography.labelSmall,
                modifier = Modifier.align(alignment)
            )
        }

        Text(
            text = fieldConfig.value,
            modifier = Modifier.align(alignment),
            color = Color(fieldConfig.valueColor),
            style = if (fieldConfig.label == null) Typography.bodyMedium else Typography.bodyLarge
        )
    }
}

@Composable
@Preview
fun FieldTextViewHeaderPreview() {
    CardKeeperTheme {
        FieldTextView(
            fieldConfig = FieldConfig(
                label = "time",
                value = "12:00",
                labelColor = 0x000000
            )
        )
    }
}

@Composable
@Preview
fun FieldTextViewAlignmentPreview() {
    CardKeeperTheme {
        FieldTextView(
            alignment = Alignment.CenterHorizontally,
            fieldConfig = FieldConfig(
                label = "time",
                value = "12:00",
                labelColor = 0x000000
            )
        )
    }
}

@Composable
@Preview
fun FieldTextViewValueOnlyPReview() {
    CardKeeperTheme {
        FieldTextView(
            fieldConfig = FieldConfig(
                label = null,
                value = "12:00",
                labelColor = 0x000000
            )
        )
    }
}
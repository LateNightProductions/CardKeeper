package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.awscherb.cardkeeper.ui.theme.Typography
import com.awscherb.cardkeeper.ui.view.FieldConfig

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
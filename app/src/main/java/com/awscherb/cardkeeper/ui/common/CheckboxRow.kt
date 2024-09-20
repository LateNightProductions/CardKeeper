package com.awscherb.cardkeeper.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awscherb.cardkeeper.ui.theme.Typography

@Composable
fun CheckboxRow(
    label: String,
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit
) {
    Row {
        Checkbox(checked = checked, onCheckedChange = onCheckedChanged)
        Text(
            text = label,
            style = Typography.bodyLarge,
            modifier = Modifier.align(
                Alignment.CenterVertically
            )
        )
    }
}

@Composable
@Preview
fun CheckboxRowCheckedPreview() {
    CheckboxRow(label = "something to think about", checked = true) { }
}


@Composable
@Preview
fun CheckboxRowUncheckedPreview() {
    CheckboxRow(label = "something to think about", checked = false) { }
}
package com.awscherb.cardkeeper.compose_common.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun RadioRow(
    selected: Boolean,
    label: String,
    onClick: () -> Unit
) {
    Row {
        RadioButton(
            selected = selected,
            onClick = {
                onClick()
            })
        Text(
            text = label,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
        )
    }
}
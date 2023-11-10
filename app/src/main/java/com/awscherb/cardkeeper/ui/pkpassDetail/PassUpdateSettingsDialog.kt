package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.ui.theme.Typography

@Composable
fun PassUpdateSettingsDialog(
    isAutoUpdateOn: Boolean,
    onDismissRequest: () -> Unit,
    onUpdateSettingsChanged: (isAutoUpdateNowOn: Boolean) -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Row {
                Checkbox(checked = isAutoUpdateOn, onCheckedChange = {
                    onUpdateSettingsChanged(it)
                })
                Text(
                    text = "Automatically update pass",
                    style = Typography.bodyLarge,
                    modifier = Modifier.align(
                        Alignment.CenterVertically
                    )
                )
            }
        }
    }
}


@Preview
@Composable
fun PassUpdateSettingsDialogPreview() {
    CardKeeperTheme {
        PassUpdateSettingsDialog(
            isAutoUpdateOn = true,
            {},
        ) {
        }
    }
}


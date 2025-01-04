package com.awscherb.cardkeeper.passdetail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.awscherb.cardkeeper.compose_common.composable.CheckboxRow
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.theme.Typography

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
            Text(
                text = "Settings",
                style = Typography.headlineSmall,
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            HorizontalDivider()
            CheckboxRow(
                label = "Automatically update pass",
                checked = isAutoUpdateOn,
                onCheckedChanged = onUpdateSettingsChanged
            )
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


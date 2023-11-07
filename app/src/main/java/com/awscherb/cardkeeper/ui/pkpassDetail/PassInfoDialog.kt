package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.ui.theme.Typography

@Composable
fun PassInfoDialog(
    items: List<Pair<String, String>>,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            LazyColumn(modifier = Modifier.padding(vertical = 20.dp)) {
                items(items = items, key = { it.first + it.second }) {
                    InfoRowItem(info = it)
                }
            }
        }
    }
}

@Composable
fun InfoRowItem(info: Pair<String, String>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = info.first,
            style = Typography.labelSmall,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp)
        )

        Text(
            text = info.second,
            style = Typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        )
    }
}

@Preview
@Composable
fun PassInfoDialogPreview() {
    CardKeeperTheme {
        PassInfoDialog(
            items = listOf(
                "Terminal" to "Terminal A",
                "Gate" to "A21",
                "Boarding Time" to "7:55pm",
                "Origin" to "Boston",
                "Destination" to "New York"
            )
        ) {
        }
    }
}

@Preview
@Composable
fun InfoRowPreview() {
    CardKeeperTheme {
        InfoRowItem(info = "Item" to "Value")
    }
}
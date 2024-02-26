package com.awscherb.cardkeeper.ui.common

import com.awscherb.cardkeeper.util.SampleTel
import com.awscherb.cardkeeper.util.extensions.toTel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.ui.common.icons.Public
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.util.extensions.toParsedUri

@Composable
fun UriView(
    text: String
) {
    val uri = text.toParsedUri() ?: return

    Column {
        LinkableRow(
            icon = Public, text = uri.title ?: uri.uri,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}


@Preview(showSystemUi = true, apiLevel = 33)
@Composable
fun UriViewPreview() {
    CardKeeperTheme {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            UriView(
                text = "https://www.google.com"
            )
        }
    }
}


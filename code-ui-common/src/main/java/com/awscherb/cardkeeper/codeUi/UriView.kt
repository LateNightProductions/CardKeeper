package com.awscherb.cardkeeper.codeUi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.compose_common.composable.LinkableRow
import com.awscherb.cardkeeper.compose_common.icons.Public
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.core.toParsedUri

@Composable
fun UriView(
    text: String
) {
    val uri = text.toParsedUri() ?: return

    Column {
        val handler = LocalUriHandler.current
        LinkableRow(
            icon = Public, text = uri.title ?: uri.uri,
            modifier = Modifier
                .padding(top = 8.dp)
                .clickable {
                    handler.openUri(uri.uri)
                }
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


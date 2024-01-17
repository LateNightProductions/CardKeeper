package com.awscherb.cardkeeper.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.util.SampleContact
import com.google.zxing.client.result.ParsedResultType

@Composable
fun CodeRichDataSection(
    data: String,
    parsedType: ParsedResultType
) {
    when (parsedType) {
        ParsedResultType.ADDRESSBOOK -> {
            ContactView(text = data)
        }

        ParsedResultType.WIFI -> {
            WifiView(text = data)
        }

        else -> {
            Text(
                modifier = Modifier
                    .padding(
                        vertical = 4.dp,
                        horizontal = 4.dp
                    )
                    .fillMaxWidth(),
                text = data,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
@Preview
fun RichDataAddress() {
    CardKeeperTheme {
        CodeRichDataSection(
            data = SampleContact,
            parsedType = ParsedResultType.ADDRESSBOOK
        )
    }
}
package com.awscherb.cardkeeper.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.compose_common.CardKeeperTheme
import com.awscherb.cardkeeper.util.SampleTel
import com.awscherb.cardkeeper.util.extensions.toTel

@Composable
fun TelView(
    text: String
) {
    val tel = text.toTel() ?: return

    Column {
        tel.number?.let { phone ->
            val handler = LocalUriHandler.current
            LinkableRow(
                icon = Icons.Default.Phone, text = phone,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable {
                        handler.openUri("tel://$phone")
                    }
            )
        }

    }
}


@Preview(showSystemUi = true, apiLevel = 33)
@Composable
fun TelViewPreview() {
    CardKeeperTheme {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            TelView(
                text = SampleTel
            )
        }
    }
}


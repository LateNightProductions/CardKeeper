package com.awscherb.cardkeeper.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.ui.common.icons.NetworkWifi
import com.awscherb.cardkeeper.ui.common.icons.Visibility
import com.awscherb.cardkeeper.ui.common.icons.VisibilityOff
import com.awscherb.cardkeeper.compose_common.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.Typography
import com.awscherb.cardkeeper.util.SampleWifi
import com.awscherb.cardkeeper.util.extensions.toWifi

@Composable
fun WifiView(
    text: String
) {
    val wifi = text.toWifi() ?: return
    var showPassword by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            Modifier.padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp
            )
        ) {

            Icon(
                NetworkWifi,
                contentDescription = "Wifi",
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            SelectionContainer {
                Text(
                    text = wifi.ssid,
                    style = Typography.titleLarge,
                    modifier = Modifier.padding(
                        start = 8.dp,
                    )
                )
            }
        }

        Row(
            Modifier
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .clickable {
                    showPassword = !showPassword
                }
        ) {

            Icon(
                if (showPassword) Visibility else VisibilityOff,
                contentDescription = "Wifi",
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            SelectionContainer {
                Text(
                    text = if (showPassword) wifi.password else "*".repeat(wifi.password.length),
                    style = Typography.titleLarge,
                    modifier = Modifier.padding(
                        start = 8.dp,
                    )
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, apiLevel = 33)
fun WifiPreview() {
    CardKeeperTheme {

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            WifiView(text = SampleWifi)
        }
    }
}


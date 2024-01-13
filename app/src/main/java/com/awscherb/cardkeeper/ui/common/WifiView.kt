package com.awscherb.cardkeeper.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.ui.common.icons.NetworkWifi
import com.awscherb.cardkeeper.ui.common.icons.Password
import com.awscherb.cardkeeper.ui.common.icons.WifiPassword
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.ui.theme.Typography
import com.awscherb.cardkeeper.util.SampleWifi
import com.awscherb.cardkeeper.util.extensions.toWifi

@Composable
fun WifiView(
    text: String
) {
    val wifi = text.toWifi() ?: return

    Column {
        Row(
            Modifier.padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp
            )
        ) {

            Icon(
                Password,
                contentDescription = "Wifi",
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            Text(
                text = wifi.ssid,
                style = Typography.titleLarge,
                modifier = Modifier.padding(
                    start = 8.dp,
                )
            )
        }

        // contact.phoneNumbers.firstOrNull()?.let { phone ->
        //     LinkableRow(
        //         icon = Icons.Default.Phone, text = phone,
        //         modifier = Modifier.padding(top = 8.dp)
        //     )
        // }
        //
        // contact.emails.firstOrNull()?.let { email ->
        //     LinkableRow(icon = Icons.Default.Email, text = email)
        // }
    }
}

@Composable
@Preview(showSystemUi = true)
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


package com.awscherb.cardkeeper.ui.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.ui.common.icons.NetworkWifi
import com.awscherb.cardkeeper.ui.common.icons.Public
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.util.SampleContact
import com.awscherb.cardkeeper.util.extensions.toAddressBook
import com.awscherb.cardkeeper.util.extensions.toEmail
import com.awscherb.cardkeeper.util.extensions.toParsedUri
import com.awscherb.cardkeeper.util.extensions.toTel
import com.awscherb.cardkeeper.util.extensions.toWifi
import com.google.zxing.client.result.ParsedResultType

@Composable
fun ScannedCodeTextPreview(
    modifier: Modifier = Modifier,
    text: String,
    parsedResultType: ParsedResultType
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        when (parsedResultType) {
            ParsedResultType.ADDRESSBOOK -> {
                val contact = text.toAddressBook() ?: return
                IconRow(
                    icon = Icons.Default.Person, text =
                    contact.names.firstOrNull() ?: ""
                )
            }

            ParsedResultType.WIFI -> {
                val wifi = text.toWifi() ?: return
                IconRow(NetworkWifi, text = wifi.ssid)
            }

            ParsedResultType.TEL -> {
                val tel = text.toTel() ?: return
                IconRow(Icons.Default.Phone, text = tel.number)
            }

            ParsedResultType.EMAIL_ADDRESS -> {
                val email = text.toEmail() ?: return
                IconRow(icon = Icons.Default.Email, text = email.subject ?: "")
            }

            ParsedResultType.URI -> {
                val uri = text.toParsedUri() ?: return
                IconRow(icon = Public, text = uri.title ?: uri.uri)
            }

            else -> {
                SelectionContainer {
                    Text(
                        modifier = Modifier
                            .padding(
                                start = 4.dp,
                                end = 4.dp,
                                bottom = 8.dp
                            )
                            .fillMaxWidth()
                            .align(CenterHorizontally),
                        text = text,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.IconRow(
    icon: ImageVector,
    text: String
) {
    Row(
        modifier = Modifier
            .align(CenterHorizontally)
            .padding(
                start = 4.dp,
                end = 4.dp,
                bottom = 8.dp
            )
    ) {
        Icon(
            icon,
            "Contact",
            modifier = Modifier
                .padding(
                    end = 8.dp
                )
                .align(Alignment.CenterVertically)
        )
        SelectionContainer {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = text,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun ScannedPreviewTextPreview() {
    CardKeeperTheme {
        ScannedCodeTextPreview(
            text = SampleContact, parsedResultType =
            ParsedResultType.TEXT
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun ScannedCodeContextPreview() {
    CardKeeperTheme {
        ScannedCodeTextPreview(
            text = SampleContact, parsedResultType =
            ParsedResultType.ADDRESSBOOK
        )
    }
}
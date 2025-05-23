package com.awscherb.cardkeeper.codeUi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.compose_common.composable.LinkableRow
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.theme.Typography
import com.awscherb.cardkeeper.compose_common.util.SampleContact
import com.awscherb.cardkeeper.core.toAddressBook

@Composable
fun ContactView(
    text: String
) {
    val contact = text.toAddressBook() ?: return

    Column {
        contact.names.firstOrNull()?.let { firstName ->
            SelectionContainer {
                Text(
                    text = firstName,
                    style = Typography.titleLarge,
                    modifier = Modifier.padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                )
            }
        }

        contact.phoneNumbers?.firstOrNull()?.let { phone ->
            LinkableRow(
                icon = Icons.Default.Phone, text = phone,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        contact.emails?.firstOrNull()?.let { email ->
            LocalUriHandler
            LinkableRow(icon = Icons.Default.Email, text = email)
        }
    }
}


@Preview(showSystemUi = true, apiLevel = 33)
@Composable
fun ContactViewPreview() {
    CardKeeperTheme {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            ContactView(
                text = SampleContact
            )
        }
    }
}


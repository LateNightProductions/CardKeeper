package com.awscherb.cardkeeper.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.compose_common.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.Typography
import com.awscherb.cardkeeper.util.SampleEmail
import com.awscherb.cardkeeper.util.extensions.toEmail

@Composable
fun EmailView(
    text: String
) {
    val email = text.toEmail() ?: return
    val recipients = StringBuilder()

    recipients.append("To: ")
    recipients.append(email.tos?.joinToString(", "))
    if (email.cCs?.isNotEmpty() == true) {
        recipients.append("\nCc: ")
        recipients.append(email.cCs?.joinToString(","))
    }

    Column {
        LinkableRow(
            icon = Icons.Default.Email, text = recipients.toString(),
            modifier = Modifier.padding(top = 8.dp)
        )

        email.subject?.let { subject ->
            SelectionContainer {
                Text(
                    text = subject,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    ),
                    style = Typography.titleSmall
                )
            }
        }

        email.body?.let { body ->
            SelectionContainer {
                Text(
                    text = body,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    ),
                    style = Typography.bodyMedium
                )
            }
        }

    }
}


@Preview(showSystemUi = true, apiLevel = 33)
@Composable
fun EmailViewPreview() {
    CardKeeperTheme {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            EmailView(
                text = SampleEmail
            )
        }
    }
}


package com.awscherb.cardkeeper.compose_common.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.theme.Typography

@Composable
fun LinkableRow(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
    ) {

        Icon(
            icon,
            text,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        SelectionContainer {
            Text(
                text = text,
                style = Typography.bodyMedium,
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp
                    )
                    .align(Alignment.CenterVertically)
            )
        }
    }
}


@Preview(apiLevel = 33)
@Composable
fun LinkablePhonePreview() {
    CardKeeperTheme {
        Card {
            LinkableRow(icon = Icons.Default.Phone, text = "312-555-0690")
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun LinkableMultiRowPreview() {
    CardKeeperTheme {
        Card {
            LinkableRow(icon = Icons.Default.Phone, text = "312-555-0690\nNext Line")
        }
    }
}
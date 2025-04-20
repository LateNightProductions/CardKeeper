package com.awscherb.cardkeeper.codeUi

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.util.SampleContact
import com.awscherb.cardkeeper.types.ExtendedTypesHelper
import com.awscherb.cardkeeper.types.ParsedResultType

@Composable
fun CodeRichDataSection(
    data: String,
    parsedType: ParsedResultType
) {
    val extendedType = ExtendedTypesHelper.matchType(data)
    when (parsedType) {
        ParsedResultType.ADDRESSBOOK -> {
            ContactView(text = data)
        }
        ParsedResultType.WIFI -> {
            WifiView(text = data)
        }
        ParsedResultType.TEL -> {
            TelView(text = data)
        }
        ParsedResultType.EMAIL_ADDRESS -> {
            EmailView(text = data)
        }
        ParsedResultType.URI -> {
            UriView(text = data)
        }
        ParsedResultType.DRIVER_LICENSE -> {
            DriverLicenseView(text = data)
        }
        else -> {
            SelectionContainer {
                Text(
                    modifier = Modifier
                        .padding(
                            vertical = 16.dp,
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

}

@Composable
@Preview(apiLevel = 33)
fun RichDataAddress() {
    CardKeeperTheme {
        CodeRichDataSection(
            data = SampleContact,
            parsedType = ParsedResultType.ADDRESSBOOK
        )
    }
}
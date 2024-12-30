package com.awscherb.cardkeeper.ui.common

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
import com.awscherb.cardkeeper.barcode.model.DriverLicenseType
import com.awscherb.cardkeeper.barcode.model.ExtendedTypesHelper
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.util.SampleContact
import com.google.zxing.client.result.ParsedResultType

@Composable
fun CodeRichDataSection(
    data: String,
    parsedType: ParsedResultType
) {
    val extendedType = ExtendedTypesHelper.matchType(data)
    when {
        parsedType == ParsedResultType.ADDRESSBOOK -> {
            ContactView(text = data)
        }

        parsedType == ParsedResultType.WIFI -> {
            WifiView(text = data)
        }

        parsedType == ParsedResultType.TEL -> {
            TelView(text = data)
        }

        parsedType == ParsedResultType.EMAIL_ADDRESS -> {
            EmailView(text = data)
        }

        parsedType == ParsedResultType.URI -> {
            UriView(text = data)
        }

        extendedType is DriverLicenseType -> {
            DriverLicenseView(license = extendedType)
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
package com.awscherb.cardkeeper.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.ui.common.icons.Cake
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.ui.theme.Typography
import com.awscherb.cardkeeper.barcode.model.DriverLicenseType
import com.awscherb.cardkeeper.barcode.model.ExtendedTypesHelper
import com.awscherb.cardkeeper.util.SampleLicense

@Composable
fun DriverLicenseView(
    license: DriverLicenseType
) {

    Column {
        val names = "${license.firstName} ${license.lastName}"
        SelectionContainer {
            Text(
                text = names,
                style = Typography.titleLarge,
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                )
            )
        }

        val address = with(StringBuilder()) {
            append(license.streetAddress)
            append("\n")
            append(license.city)
            append(", ")
            append(license.state)
            append(" ")
            append(license.postal)
            toString()
        }

        LinkableRow(
            icon = Icons.Default.Home, text = address,
            modifier = Modifier.padding(top=8.dp)
        )

        LinkableRow(
            icon = Cake, text = license.dob,
        )

        LinkableRow(
            icon = Icons.Default.DateRange, text = "Issued ${license.issueDate}",
        )


        LinkableRow(
            icon = Icons.Default.DateRange, text = "Expires ${license.expiration}",
        )



    }
}


@Preview(showSystemUi = true, apiLevel = 33)
@Composable
fun DriverLicensePreviewPreview() {
    CardKeeperTheme {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            DriverLicenseView(
                ExtendedTypesHelper.matchType(SampleLicense) as DriverLicenseType
            )
        }
    }
}

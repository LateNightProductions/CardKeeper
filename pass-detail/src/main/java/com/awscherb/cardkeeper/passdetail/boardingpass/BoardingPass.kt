package com.awscherb.cardkeeper.passdetail.boardingpass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.awscherb.cardkeeper.passUi.FieldTextView
import com.awscherb.cardkeeper.passdetail.R
import com.awscherb.cardkeeper.passdetail.model.PassDetailModel
import com.awscherb.cardkeeper.passdetail.util.getAlignmentForFieldText

/**
 * |Primary| |Icon| |Primary|
 * |        Aux fields      |
 * |    Secondary Fields    |
 * |                        |
 * |         Footer         |
 *
 * Header (top) and barcode (bottom) are assumed to be rendered in the parent
 */
@Composable
fun ColumnScope.BoardingPass(pass: PassDetailModel) {

    pass.transit!!
    TransitPrimarySection(
        modifier = Modifier.padding(top = 8.dp),
        fromAirport = pass.transit.originLabel,
        fromCode = pass.transit.originValue,
        toAirport = pass.transit.destinationLabel,
        toCode = pass.transit.destinationValue,
        tint = pass.foregroundColor,
        transitType = pass.transit.type
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        pass.auxiliaryFields.forEachIndexed { index, field ->
            val align =
                getAlignmentForFieldText(
                    index,
                    pass.auxiliaryFields.size
                )
            FieldTextView(
                alignment = align,
                fieldConfig = field
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        pass.secondaryFields.forEachIndexed { index, field ->
            val align =
                getAlignmentForFieldText(index, pass.secondaryFields.size)
            FieldTextView(
                alignment = align,
                fieldConfig = field
            )
        }
    }

    pass.footerPath?.let { footer ->
        AsyncImage(
            model = footer,
            placeholder = painterResource(id = R.drawable.footer_placeholder),
            modifier = Modifier

                .fillMaxWidth(.5f)
                .align(CenterHorizontally)
                .padding(top = 16.dp, bottom = 4.dp),
            contentDescription = "Pass Footer",
        )
    }
}

package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.awscherb.cardkeeper.pkpass.model.PassInfo
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.findOriginDestination
import com.awscherb.cardkeeper.pkpass.model.getTransitType
import com.awscherb.cardkeeper.pkpass.model.getTranslatedLabel
import com.awscherb.cardkeeper.pkpass.model.getTranslatedValue
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
import com.awscherb.cardkeeper.ui.common.getAlignmentForFieldText
import com.awscherb.cardkeeper.ui.common.FieldConfig
import com.awscherb.cardkeeper.util.extensions.getForegroundColor

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
fun ColumnScope.BoardingPass(pass: PkPassModel, passInfo: PassInfo) {

    val (origin, destination) = passInfo.findOriginDestination()
    TransitPrimarySection(
        modifier = Modifier.padding(top = 8.dp),
        fromAirport = origin.label ?: "",
        fromCode = origin.value,
        toAirport = destination.label ?: "",
        toCode = destination.value,
        tint = pass.getForegroundColor(),
        transitType = passInfo.getTransitType()
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        passInfo.auxiliaryFields?.forEachIndexed { index, field ->
            val align =
                getAlignmentForFieldText(
                    index,
                    passInfo.auxiliaryFields?.size ?: 0
                )
            FieldTextView(
                alignment = align,
                fieldConfig = FieldConfig(
                    label = pass.getTranslatedLabel(field.label),
                    value = pass.getTranslatedValue(field.value),
                    labelColor = pass.labelColor.parseHexColor(),
                    valueColor = pass.foregroundColor.parseHexColor()
                )
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        passInfo.secondaryFields?.forEachIndexed { index, field ->
            val align =
                getAlignmentForFieldText(index, passInfo.secondaryFields?.size ?: 0)
            FieldTextView(
                alignment = align,
                fieldConfig = FieldConfig(
                    label = pass.getTranslatedLabel(field.label),
                    value = pass.getTranslatedValue(field.value),
                    labelColor = pass.labelColor.parseHexColor(),
                    valueColor = pass.foregroundColor.parseHexColor()
                )
            )
        }
    }

    pass.footerPath?.let { footer ->
        AsyncImage(
            model = footer,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(top = 16.dp),
            contentDescription = "Pass Footer",
        )
    }
}

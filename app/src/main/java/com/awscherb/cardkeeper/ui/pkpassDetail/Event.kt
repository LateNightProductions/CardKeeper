package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.pkpass.model.PassInfo
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.getTranslatedLabel
import com.awscherb.cardkeeper.pkpass.model.getTranslatedValue
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
import com.awscherb.cardkeeper.ui.common.FieldConfig
import com.awscherb.cardkeeper.ui.common.getAlignmentForFieldText

/**
 * |        Primary         |
 * |                        |
 * |     Secondary          |
 * |      Aux               |
 * |         Barcode        |
 */
@Composable
fun Event(pass: PkPassModel, passInfo: PassInfo) {
    Box {
        pass.stripPath?.let {
            AsyncImage(
                model = it,
                placeholder = painterResource(id = R.drawable.strip_placeholder),
                modifier = Modifier.fillMaxWidth(),
                contentDescription = "Pass Image"
            )
        }

        Row {
            passInfo.primaryFields?.firstOrNull()?.let { primary ->
                EventTextView(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(.75f),
                    pass = pass,
                    primary = primary
                )
            }

            pass.thumbnailPath?.let { thumbnail ->
                AsyncImage(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .align(Alignment.CenterVertically)
                        .weight(.25f),
                    model = thumbnail,
                    contentDescription = "Thumbnail",
                )
            }
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
                getAlignmentForFieldText(
                    index,
                    passInfo.secondaryFields?.size ?: 0
                )
            FieldTextView(
                alignment = align,
                fieldConfig = FieldConfig(
                    label = pass.getTranslatedLabel(field.label),
                    value = pass.getTranslatedValue(field.typedValue),
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
                    value = pass.getTranslatedValue(field.typedValue),
                    labelColor = pass.labelColor.parseHexColor(),
                    valueColor = pass.foregroundColor.parseHexColor()
                )
            )
        }
    }
}
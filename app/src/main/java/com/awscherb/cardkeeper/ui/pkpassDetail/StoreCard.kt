package com.awscherb.cardkeeper.ui.pkpassDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.passUi.FieldConfig
import com.awscherb.cardkeeper.passUi.FieldTextView
import com.awscherb.cardkeeper.pkpass.model.PassInfo
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.getTranslatedLabel
import com.awscherb.cardkeeper.pkpass.model.getTranslatedValue
import com.awscherb.cardkeeper.pkpass.model.parseHexColor
import com.awscherb.cardkeeper.ui.common.getAlignmentForFieldText

/**
 * |        Primary         |
 * |    Aux & Secondary     |
 * |                        |
 * |         Footer         |
 */
@Composable
fun StoreCard(pass: PkPassModel, passInfo: PassInfo) {

    Box {
        pass.stripPath?.let {
            AsyncImage(
                model = it,
                placeholder = painterResource(id = R.drawable.strip_placeholder),
                modifier = Modifier.fillMaxWidth(),
                contentDescription = "Pass Image"
            )
        }

        passInfo.primaryFields?.firstOrNull()?.let { primary ->
            PrimaryTextView(
                modifier = Modifier.padding(horizontal = 8.dp),
                pass = pass,
                primary = primary
            )
        }
    }

    val allFields = (passInfo.auxiliaryFields ?: emptyList()) +
            (passInfo.secondaryFields ?: emptyList())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        allFields.forEachIndexed { index, field ->
            val align =
                getAlignmentForFieldText(
                    index,
                    allFields.size
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

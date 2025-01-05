package com.awscherb.cardkeeper.passdetail

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
import com.awscherb.cardkeeper.passUi.FieldTextView
import com.awscherb.cardkeeper.passdetail.util.getAlignmentForFieldText

/**
 * |        Primary         |
 * |                        |
 * |     Secondary & aux    |
 * |         Barcode        |
 */
@Composable
fun Coupon(pass: PassDetailModel) {
    Box {
        pass.stripPath?.let {
            AsyncImage(
                model = it,
                placeholder = painterResource(id = R.drawable.strip_placeholder),
                modifier = Modifier.fillMaxWidth(),
                contentDescription = "Pass Image"
            )
        }

        pass.primaryFields.firstOrNull()?.let { primary ->
            PrimaryTextView(
                modifier = Modifier.padding(horizontal = 8.dp),
                pass = pass,
                primary = primary
            )
        }
    }

    val allFields = (pass.auxiliaryFields) + (pass.secondaryFields)
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
                fieldConfig = field
            )
        }
    }
}
package com.awscherb.cardkeeper.passdetail.generic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.awscherb.cardkeeper.passUi.FieldTextView
import com.awscherb.cardkeeper.passdetail.common.PrimaryTextView
import com.awscherb.cardkeeper.passdetail.model.PassDetailModel
import com.awscherb.cardkeeper.passdetail.util.getAlignmentForFieldText

/**
 * |    Primary [thumb]     |
 * |    secondary           |
 * |    aux                 |
 * |  rect barcode          |
 */

/**
 * |    Primary [thumb]     |
 * |    secondary & aux     |
 * |  square barcode        |
 */


@Composable
fun Generic(pass: PassDetailModel) {

    Row {
        pass.primaryFields.firstOrNull()?.let { primary ->
            PrimaryTextView(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(.75f),
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

    val allFields = if (pass.isBarcodeSquare)
        pass.auxiliaryFields + pass.secondaryFields
    else pass.secondaryFields
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

    if (!pass.isBarcodeSquare) {
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
    }
}
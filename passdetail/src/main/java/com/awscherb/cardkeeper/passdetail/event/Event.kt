package com.awscherb.cardkeeper.passdetail.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.passUi.FieldTextView
import com.awscherb.cardkeeper.passdetail.model.PassDetailModel
import com.awscherb.cardkeeper.passdetail.R
import com.awscherb.cardkeeper.passdetail.util.getAlignmentForFieldText

/**
 * |        Primary         |
 * |                        |
 * |     Secondary          |
 * |      Aux               |
 * |         Barcode        |
 */
@Composable
fun Event(pass: PassDetailModel) {
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
            pass.primaryFields.firstOrNull()?.let { primary ->
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
        pass.secondaryFields.forEachIndexed { index, field ->
            val align =
                getAlignmentForFieldText(
                    index,
                    pass.secondaryFields.size
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

@Preview(showSystemUi = true)
@Composable
fun EventStripPreview() {
    CardKeeperTheme {
        Card {
            Column {
//                Event(pass = SampleEvent, passInfo = SampleEvent.findPassInfo()!!)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun EventBackgroundPreview() {
    CardKeeperTheme {
        Card {
            Column {
//                Event(pass = SampleEvent2, passInfo = SampleEvent.findPassInfo()!!)
            }
        }
    }
}


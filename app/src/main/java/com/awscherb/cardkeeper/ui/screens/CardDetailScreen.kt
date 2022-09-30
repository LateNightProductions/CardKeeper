package com.awscherb.cardkeeper.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.ui.card_detail.CardDetailViewModel


@Composable
fun CardDetailScreen(
    viewModel: CardDetailViewModel
)

@Composable
fun CardDetailItem(cardItem: ScannedCode, onClick: (ScannedCode) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),

        ) {
        Column {
            Text(
                text = cardItem.title, modifier = Modifier.padding(16.dp)
            )
            ScannedCodeImage(cardItem = cardItem)
        }
    }

}


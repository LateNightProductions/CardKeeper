package com.awscherb.cardkeeper.ui.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.core.SavedItem
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.ui.view.ScaffoldScreen

@Composable
fun ItemsScreen(
    items: List<SavedItem>,
    onClick: (SavedItem) -> Unit
) {
    ScaffoldScreen(title = "Items", navOnClick = { /*TODO*/ }) {

        LazyColumn(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(
                top = 8.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 64.dp
            )
        ) {
            items(items, key = { it.id }) { item ->
                when (item) {
                    is ScannedCodeModel -> ScannedCodeItem(
                        item = item,
                    ) { onClick(it) }

                    is PkPassModel -> PassItem(pass = item) {
                        onClick(it)
                    }
                }

            }
        }
    }
}
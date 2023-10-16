package com.awscherb.cardkeeper.ui.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.core.SavedItem
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.ui.view.ScaffoldScreen

@Composable
fun ItemsScreen(
    viewModel: ItemsViewModel = hiltViewModel(),
    navOnClick: () -> Unit,
    scanOnClick: () -> Unit,
    onClick: (SavedItem) -> Unit
) {
    val items by viewModel.items.collectAsState(initial = emptyList())

    ScaffoldScreen(
        title = "Items",
        navOnClick = navOnClick,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                scanOnClick()
            }) {
                Icon(painterResource(id = R.drawable.ic_scan), contentDescription = null)
            }
        }
    ) {
        ItemsList(items, it, onClick)
    }
}

@Composable
fun ItemsList(
    items: List<SavedItem>,
    paddingValues: PaddingValues,
    onClick: (SavedItem) -> Unit
) {
    var size by remember {
        mutableStateOf(Size.Zero)
    }

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .onGloballyPositioned { size = it.size.toSize() },
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
                    size = size,
                ) { onClick(it) }

                is PkPassModel -> PassItem(pass = item) {
                    onClick(it)
                }
            }

        }
    }
}


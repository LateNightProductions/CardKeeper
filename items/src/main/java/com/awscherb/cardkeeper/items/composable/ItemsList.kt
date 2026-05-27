package com.awscherb.cardkeeper.items.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.items.model.GroupedPassItemModel
import com.awscherb.cardkeeper.items.model.ItemModel
import com.awscherb.cardkeeper.items.model.PassItemModel
import com.awscherb.cardkeeper.items.model.ScannedCodeItemModel
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Composable
fun ItemsList(
    items: List<ItemModel>,
    paddingValues: PaddingValues,
    onClick: (ItemModel) -> Unit,
    reorderMode: Boolean = false,
    onMove: (Int, Int) -> Unit = { _, _ -> }
) {
    val lazyListState = rememberLazyListState()
    val reorderableState = rememberReorderableLazyListState(lazyListState) { from, to ->
        onMove(from.index, to.index)
    }

    LazyColumn(
        state = lazyListState,
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(
            top = 8.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 88.dp
        )
    ) {
        items(
            items,
            key = { it.id },
            contentType = { it::class }
        ) { item ->
            ReorderableItem(reorderableState, key = item.id) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val itemModifier = Modifier.weight(1f)
                    when (item) {
                        is ScannedCodeItemModel -> ScannedCodeItem(
                            item = item,
                            modifier = itemModifier
                        ) { if (!reorderMode) onClick(it) }

                        is PassItemModel -> PassItem(
                            pass = item,
                            modifier = itemModifier
                        ) { if (!reorderMode) onClick(it) }

                        is GroupedPassItemModel -> GroupedPassItem(
                            group = item,
                            modifier = itemModifier
                        ) { if (!reorderMode) onClick(it) }
                    }
                    if (reorderMode) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Drag to reorder",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .draggableHandle()
                        )
                    }
                }
            }
        }
    }
}

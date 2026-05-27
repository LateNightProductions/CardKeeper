package com.awscherb.cardkeeper.items.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awscherb.cardkeeper.compose_common.theme.ScaffoldScreen
import com.awscherb.cardkeeper.compose_common.theme.SearchableScaffoldScreen
import com.awscherb.cardkeeper.items.R
import com.awscherb.cardkeeper.items.model.ItemModel
import com.awscherb.cardkeeper.items.model.SortOptions
import com.awscherb.cardkeeper.items.vm.ItemsViewModel

@Composable
fun ItemsScreen(
    viewModel: ItemsViewModel = hiltViewModel(),
    navOnClick: () -> Unit,
    scanOnClick: () -> Unit,
    onClick: (ItemModel) -> Unit
) {

    val items by viewModel.items.collectAsStateWithLifecycle(emptyList())
    val reorderMode by viewModel.reorderMode.collectAsStateWithLifecycle()
    val reorderItems by viewModel.reorderItems.collectAsStateWithLifecycle()
    val sort by viewModel.sort.collectAsStateWithLifecycle()

    var showSort by remember { mutableStateOf(false) }

    if (reorderMode) {
        ScaffoldScreen(
            title = "Reorder",
            navOnClick = { viewModel.cancelReorderMode() },
            navIcon = Icons.Default.Close,
            topBarActions = {
                IconButton(onClick = { viewModel.saveReorder() }) {
                    Icon(Icons.Default.Check, "Save order")
                }
            }
        ) { paddingValues ->
            ItemsList(
                items = reorderItems,
                paddingValues = paddingValues,
                onClick = {},
                reorderMode = true,
                onMove = { from, to -> viewModel.moveItem(from, to) }
            )
        }
    } else {
        SearchableScaffoldScreen(
            title = "Items",
            navOnClick = navOnClick,
            floatingActionButton = {
                FloatingActionButton(onClick = { scanOnClick() }) {
                    Icon(painterResource(id = R.drawable.ic_scan), contentDescription = null)
                }
            },
            onQueryChanged = { viewModel.searchQuery.value = it },
            onSearchCleared = { viewModel.searchQuery.value = "" },
            topBarActions = {
                if (sort is SortOptions.Default) {
                    IconButton(onClick = { viewModel.enterReorderMode(items) }) {
                        Icon(Icons.Default.List, "Reorder items")
                    }
                }
                IconButton(onClick = { showSort = true }) {
                    Icon(Icons.Default.Menu, "Sort and filter")
                }
            }
        ) { paddingValues ->
            ItemsList(items, paddingValues, onClick)
            if (showSort) {
                SortAndFilterDialog(
                    viewModel = viewModel,
                    onDismissRequest = { showSort = false },
                )
            }
        }
    }
}

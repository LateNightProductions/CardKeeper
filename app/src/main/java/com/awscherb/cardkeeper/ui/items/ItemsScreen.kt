package com.awscherb.cardkeeper.ui.items

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.core.SavedItem
import com.awscherb.cardkeeper.ui.common.SearchableScaffoldScreen

@Composable
fun ItemsScreen(
    viewModel: ItemsViewModel = hiltViewModel(),
    navOnClick: () -> Unit,
    scanOnClick: () -> Unit,
    onClick: (SavedItem) -> Unit
) {

    val items by viewModel.items.collectAsState(initial = emptyList())

    var showSort by remember {
        mutableStateOf(false)
    }

    SearchableScaffoldScreen(
        title = "Items",
        navOnClick = navOnClick,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                scanOnClick()
            }) {
                Icon(painterResource(id = R.drawable.ic_scan), contentDescription = null)
            }
        },
        onQueryChanged = {
            viewModel.searchQuery.value = it
        },
        onSearchCleared = {
            viewModel.searchQuery.value = ""
        },
        topBarActions = {
            IconButton(onClick = { showSort = true }) {
                Icon(Icons.AutoMirrored.Filled.Sort, "Sort and filter")
            }
        }
    ) {
        ItemsList(items, it, onClick)
        if (showSort) {
            SortAndFilterDialog(
                viewModel = viewModel,
                onDismissRequest = { showSort = false },
            )
        }
    }
}


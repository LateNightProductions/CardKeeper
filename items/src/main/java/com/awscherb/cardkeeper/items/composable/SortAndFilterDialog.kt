package com.awscherb.cardkeeper.items.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.awscherb.cardkeeper.compose_common.composable.CheckboxRow
import com.awscherb.cardkeeper.compose_common.composable.RadioRow
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.theme.Typography
import com.awscherb.cardkeeper.items.model.FilterOptions
import com.awscherb.cardkeeper.items.vm.ItemsViewModel
import com.awscherb.cardkeeper.items.model.SortOptions

@Composable
fun SortAndFilterDialog(
    viewModel: ItemsViewModel,
    onDismissRequest: () -> Unit = {}
) {

    val filter by viewModel.filter.collectAsState()
    val sort by viewModel.sort.collectAsState()
    val showExpiredPasses by viewModel.showExpiredPasses.collectAsState()

    SortAndFilterDialogContent(
        initialFilter = filter,
        onFilterChanged = { newFilter ->
            viewModel.filter.value = newFilter
        },
        initialSort = sort,
        onSortChanged = { newSort ->
            viewModel.sort.value = newSort
        },
        showExpiredPasses = showExpiredPasses,
        onShowExpiredPassesChanged = viewModel.showExpiredPasses::tryEmit,
        onDismissRequest = onDismissRequest
    )

}

@Composable
fun SortAndFilterDialogContent(
    onDismissRequest: () -> Unit,
    initialFilter: FilterOptions = FilterOptions.All,
    onFilterChanged: (newFilter: FilterOptions) -> Unit,
    showExpiredPasses: Boolean = true,
    onShowExpiredPassesChanged: (Boolean) -> Unit = {},
    initialSort: SortOptions = SortOptions.Date(ascending = false),
    onSortChanged: (newSort: SortOptions) -> Unit,
) {
    var selectedFilter by remember {
        mutableStateOf(initialFilter)
    }

    var selectedSort by remember {
        mutableStateOf(initialSort)
    }

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                text = "Sort and filter",
                style = Typography.headlineSmall,
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            HorizontalDivider()
            Column(
                modifier = Modifier.padding(
                    horizontal = 8.dp
                )
            ) {
                Text(
                    text = "Filter",
                    style = Typography.titleMedium,
                    modifier = Modifier
                        .padding(
                            horizontal = 8.dp,
                            vertical = 8.dp
                        )
                )
                ALL_FILTER.forEach { opt ->
                    RadioRow(
                        selected = selectedFilter == opt,
                        label = opt.label
                    ) {
                        onFilterChanged(opt)
                        selectedFilter = opt
                    }

                }
                HorizontalDivider()
                Text(
                    text = "Pass Options",
                    style = Typography.titleMedium,
                    modifier = Modifier
                        .padding(
                            horizontal = 8.dp,
                            vertical = 8.dp
                        )
                )
                CheckboxRow(
                    label = "Show expired passes",
                    checked = showExpiredPasses,
                    onCheckedChanged = onShowExpiredPassesChanged
                )
                HorizontalDivider()
                Row {
                    Text(
                        text = "Sort",
                        style = Typography.titleMedium,
                        modifier = Modifier
                            .weight(1f)
                            .padding(
                                horizontal = 8.dp,
                                vertical = 8.dp
                            )
                    )

                    val ascLabel: String
                    val ascIcon: ImageVector
                    when (selectedSort.ascending) {
                        true -> {
                            ascLabel = "Ascending"
                            ascIcon = Icons.Default.KeyboardArrowUp
                        }

                        false -> {
                            ascLabel = "Descending"
                            ascIcon = Icons.Default.KeyboardArrowDown
                        }
                    }

                    Row(
                        modifier = Modifier.clickable {
                            val newSort = selectedSort.invert()
                            selectedSort = newSort
                            onSortChanged(newSort)
                        }
                    ) {
                        Text(
                            text = ascLabel,
                            style = Typography.titleMedium,
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                        )
                        Icon(
                            ascIcon, ascLabel,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                    }

                }
                RadioRow(
                    selected = selectedSort is SortOptions.Date,
                    label = if (selectedSort.ascending) "Date added (oldest first)" else "Date added (newest first)"
                ) {
                    onSortChanged(SortOptions.Date(selectedSort.ascending))
                    selectedSort = SortOptions.Date(selectedSort.ascending)
                }
            }
        }
    }

}

val ALL_FILTER = listOf(
    FilterOptions.All,
    FilterOptions.QrCodes,
    FilterOptions.Passes
)

@Composable
@Preview(showSystemUi = true, apiLevel = 33)
fun SortAndFilterPreview() {
    CardKeeperTheme {
        SortAndFilterDialogContent(
            onDismissRequest = {},
            initialFilter = FilterOptions.All,
            onFilterChanged = {},
            initialSort = SortOptions.Date(true),
            onSortChanged = {}
        )

    }
}
package com.awscherb.cardkeeper.ui.items

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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.awscherb.cardkeeper.ui.common.RadioRow
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.ui.theme.Typography

@Composable
fun SortAndFilterDialog(
    onDismissRequest: () -> Unit,
    initialFilter: FilterOptions = FilterOptions.All,
    onFilterChanged: (newFilter: FilterOptions) -> Unit,
    initialSort: SortOptions = SortOptions.Date(ascending = false),
    onSortChanged: (newSort: SortOptions) -> Unit,
) {
    var selectedFilter by remember {
        mutableStateOf(initialFilter)
    }

    var selectedSort by remember {
        mutableStateOf(initialSort)
    }

    var ascending by remember {
        mutableStateOf(false)
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
            Divider()
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
                Divider()
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
@Preview(showSystemUi = true)
fun SortAndFilterPreview() {
    CardKeeperTheme {
        SortAndFilterDialog(
            onDismissRequest = {},
            initialFilter = FilterOptions.All,
            onFilterChanged = {},
            initialSort = SortOptions.Date(true),
            onSortChanged = {}
        )

    }
}
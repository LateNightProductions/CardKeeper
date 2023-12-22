package com.awscherb.cardkeeper.ui.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.ui.theme.Typography

@Composable
fun SortAndFilterDialog(
    onDismissRequest: () -> Unit ,
    initialFilter: FilterOptions = FilterOptions.All,
    onFilterChanged: (newFilter: FilterOptions) -> Unit
) {
    var selectedFilter by remember {
        mutableStateOf(initialFilter)
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
            Column {
                Text(
                    text = "Filter",
                    style = Typography.titleMedium,
                    modifier = Modifier
                        .padding(
                            horizontal = 8.dp,
                            vertical = 8.dp
                        )
                )
                ALL_OPTIONS.forEach { opt ->

                    Row {
                        RadioButton(
                            selected =
                            selectedFilter == opt,
                            onClick = {
                                onFilterChanged(opt)
                                selectedFilter = opt
                            })
                        Text(
                            text = opt.label,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}

val ALL_OPTIONS = listOf(
    FilterOptions.All,
    FilterOptions.QrCodes,
    FilterOptions.Passes
)

@Composable
@Preview
fun SortAndFilterPreview() {
    CardKeeperTheme {
        SortAndFilterDialog({},
            initialFilter = FilterOptions.Passes
        ) {}
    }
}
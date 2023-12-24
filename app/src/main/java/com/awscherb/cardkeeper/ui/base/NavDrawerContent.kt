package com.awscherb.cardkeeper.ui.base

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.ui.theme.CardKeeperTheme
import com.awscherb.cardkeeper.ui.theme.Typography

@Composable
fun NavDrawerContent(
    selectedItem: Destination,
    topLevelNav: (Destination, Boolean) -> Unit
) {
    ModalDrawerSheet {
        Text(
            text = "CardKeeper",
            modifier = Modifier.padding(
                top = 36.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 24.dp
            ),
            style = Typography.headlineLarge
        )
        Divider()
        NavDrawerRow(
            selectedItem = selectedItem,
            destination = Destination.Items,
            topLevelNav = topLevelNav
        )
        NavDrawerRow(
            selectedItem = selectedItem,
            destination = Destination.Scan,
            topLevelNav = topLevelNav
        )
        NavDrawerRow(
            selectedItem = selectedItem,
            destination = Destination.Create,
            topLevelNav = topLevelNav
        )
        Divider()
        NavDrawerRow(
            selectedItem = selectedItem,
            destination = Destination.About,
            topLevelNav = topLevelNav
        )
    }
}

@Composable
fun NavDrawerRow(
    selectedItem: Destination,
    destination: Destination,
    topLevelNav: (Destination, Boolean) -> Unit
) {
    NavigationDrawerItem(
        label = {
            Row {
                Icon(destination.icon!!, destination.label)
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterVertically),
                    text = destination.label
                )
            }
        },
        selected = selectedItem == destination,
        onClick = {
            topLevelNav(destination, true)
        })
}

@Composable
@Preview
fun DrawerPreview() {
    CardKeeperTheme {
        NavDrawerContent(selectedItem = Destination.Scan, topLevelNav = { _, _ -> })
    }
}
package com.awscherb.cardkeeper.ui.base

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.compose_common.theme.CardKeeperTheme
import com.awscherb.cardkeeper.compose_common.theme.Typography

@Composable
fun NavDrawerContent(
    selectedItem: Destination,
    topLevelNav: (Destination) -> Unit
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
        HorizontalDivider()
        Content.MainSectionItems.forEach { dest ->
            NavDrawerRow(
                selectedItem = selectedItem,
                destination = dest,
                topLevelNav = topLevelNav
            )
        }
        HorizontalDivider()
        Content.SecondaryItems.forEach { dest ->
            NavDrawerRow(
                selectedItem = selectedItem,
                destination = dest,
                topLevelNav = topLevelNav
            )
        }
    }
}

@Composable
fun NavDrawerRow(
    selectedItem: Destination,
    destination: Destination,
    topLevelNav: (Destination) -> Unit
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
            topLevelNav(destination)
        })
}

@Composable
@Preview(apiLevel = 33, showSystemUi = true)
fun DrawerPreview() {
    CardKeeperTheme {
        NavDrawerContent(selectedItem = Destination.Scan, topLevelNav = { _ -> })
    }
}
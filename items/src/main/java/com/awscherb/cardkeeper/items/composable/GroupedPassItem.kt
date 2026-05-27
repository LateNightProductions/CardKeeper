package com.awscherb.cardkeeper.items.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.awscherb.cardkeeper.items.model.GroupedPassItemModel

@Composable
fun GroupedPassItem(
    group: GroupedPassItemModel,
    modifier: Modifier = Modifier,
    onClick: (GroupedPassItemModel) -> Unit
) {
    Box(modifier = modifier) {
        if (group.passes.size > 2) {
            PassItem(
                pass = group.passes[2],
                modifier = Modifier
                    .offset(x = 8.dp, y = (-8).dp)
                    .alpha(0.4f)
            ) {}
        }
        if (group.passes.size > 1) {
            PassItem(
                pass = group.passes[1],
                modifier = Modifier
                    .offset(x = 4.dp, y = (-4).dp)
                    .alpha(0.7f)
            ) {}
        }
        PassItem(
            pass = group.passes[0]
        ) { onClick(group) }
    }
}

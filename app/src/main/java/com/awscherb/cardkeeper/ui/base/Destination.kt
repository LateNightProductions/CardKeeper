package com.awscherb.cardkeeper.ui.base

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.awscherb.cardkeeper.ui.common.icons.Camera

sealed class Destination(
    val label: String,
    val icon: ImageVector?,
    val dest: String
) {
    data object Items : Destination(
        label = "Items",
        icon = Icons.Default.List,
        dest = "items"
    )

    data object Pass : Destination(
        label = "Pass",
        icon = null,
        dest = "pass/{passId}"
    )

    data object Code : Destination(
        label = "Code",
        icon = null,
        dest = "code/{codeId}"
    )

    data object Scan : Destination(
        label = "Scan",
        icon = Camera,
        dest = "scan"
    )

    data object Create : Destination(
        label = "Create",
        icon = Icons.Default.Edit,
        dest = "create"
    )

    data object About : Destination(
        label = "About",
        icon = Icons.Default.Info,
        dest = "about"
    )
}

object Content {
    val MAIN_SECTION_ITEMS = listOf(
        Destination.Items,
        Destination.Scan,
        Destination.Create
    )
}

package com.awscherb.cardkeeper.ui.base

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import com.awscherb.cardkeeper.ui.common.icons.Camera
import com.awscherb.cardkeeper.ui.common.icons.FileOpen

sealed class Destination(
    val label: String,
    val icon: ImageVector?,
    val dest: String
) {
    data object Items : Destination(
        label = "Items",
        icon = Icons.AutoMirrored.Default.List,
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

    data object Import : Destination(
        label = "Import pass",
        icon = FileOpen,
        dest = "import"
    )
}

object Content {
    val MainSectionItems = listOf(
        Destination.Items,
        Destination.Scan,
        Destination.Create,
        Destination.Import
    )

    val SecondaryItems = listOf<Destination>(
        Destination.About
    )
}

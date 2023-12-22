package com.awscherb.cardkeeper.ui.items

import com.awscherb.cardkeeper.core.SavedItem


sealed class SortOptions(
    val label: String,
    open val ascending: Boolean,
    val sort: (SavedItem, SavedItem) -> Int
) {

    abstract fun invert(): SortOptions

    data class Date(override val ascending: Boolean) : SortOptions(
        "Date added", ascending, { a, b ->
            (if (ascending) -1 else 1) * b.created.compareTo(a.created)
        }
    ) {
        override fun invert() = this.copy(ascending = !ascending)
    }
}
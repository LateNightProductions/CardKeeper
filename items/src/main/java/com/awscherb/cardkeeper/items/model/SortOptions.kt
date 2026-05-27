package com.awscherb.cardkeeper.items.model


sealed class SortOptions(
    val label: String,
    open val ascending: Boolean,
    val sort: (ItemModel, ItemModel) -> Int
) {

    abstract fun invert(): SortOptions

    data class Date(override val ascending: Boolean) : SortOptions(
        "Date added", ascending, { a, b ->
            (if (ascending) -1 else 1) * b.created.compareTo(a.created)
        }
    ) {
        override fun invert() = this.copy(ascending = !ascending)
    }

    data object Default : SortOptions(
        "Default", true, { a, b ->
            b.sortOrder.compareTo(a.sortOrder)
        }
    ) {
        override fun invert() = this
    }
}
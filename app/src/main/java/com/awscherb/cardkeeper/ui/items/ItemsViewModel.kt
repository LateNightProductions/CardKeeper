package com.awscherb.cardkeeper.ui.items

import androidx.lifecycle.ViewModel
import com.awscherb.cardkeeper.barcode.model.ScannedCodeModel
import com.awscherb.cardkeeper.core.SavedItem
import com.awscherb.cardkeeper.data.repository.SavedItemRepository
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val savedItemRepository: SavedItemRepository
) : ViewModel() {

    companion object {
        private fun filterForOptions(filterOptions: FilterOptions): (SavedItem) -> Boolean =
            when (filterOptions) {
                FilterOptions.All -> { _ -> true }
                FilterOptions.Passes -> { item -> item is PkPassModel }
                FilterOptions.QrCodes -> { item -> item is ScannedCodeModel }
            }
    }

    val filter = MutableStateFlow<FilterOptions>(FilterOptions.All)

    val searchQuery = MutableStateFlow("")

    val items: Flow<List<SavedItem>> = searchQuery
        .flatMapLatest { savedItemRepository.listSavedItems(it) }
        .combine(filter) { items, filter -> items.filter(filterForOptions(filter)) }

}
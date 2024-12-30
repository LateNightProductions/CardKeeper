package com.awscherb.cardkeeper.items.vm

import androidx.lifecycle.ViewModel
import com.awscherb.cardkeeper.items.model.FilterOptions
import com.awscherb.cardkeeper.items.model.SortOptions
import com.awscherb.cardkeeper.items.model.ItemModel
import com.awscherb.cardkeeper.items.model.PassItemModel
import com.awscherb.cardkeeper.items.model.ScannedCodeItemModel
import com.awscherb.cardkeeper.items.repo.ItemsRepository
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.util.PassDateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val savedItemRepository: ItemsRepository
) : ViewModel() {

    companion object {
        private fun filterForOptions(filterOptions: FilterOptions): (ItemModel) -> Boolean =
            when (filterOptions) {
                FilterOptions.All -> { _ -> true }
                FilterOptions.Passes -> { item -> item is PassItemModel }
                FilterOptions.QrCodes -> { item -> item is ScannedCodeItemModel }
            }
    }

    val filter = MutableStateFlow<FilterOptions>(FilterOptions.All)

    val sort = MutableStateFlow<SortOptions>(SortOptions.Date(ascending = false))

    val showExpiredPasses = MutableStateFlow(false)

    val searchQuery = MutableStateFlow("")

    val items: Flow<List<ItemModel>> = searchQuery
        .flatMapLatest { savedItemRepository.listSavedItems(it) }
        .combine(showExpiredPasses) { items, showExpired ->
            items.filter {
                val filterTime = System.currentTimeMillis()
                when {
                    it is PkPassModel && showExpired -> true
                    it is PkPassModel && it.expirationDate != null -> {
                        (PassDateUtils.dateStringToLocalTime(it.expirationDate!!).time) >= filterTime
                    }

                    else -> true
                }
            }
        }
        .combine(filter) { items, filter ->
            items.filter(filterForOptions(filter))
        }
        .combine(sort) { items, sort ->
            items.sortedWith(sort.sort)
        }
        .flowOn(Dispatchers.IO)

}
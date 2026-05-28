package com.awscherb.cardkeeper.items.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awscherb.cardkeeper.items.model.FilterOptions
import com.awscherb.cardkeeper.items.model.GroupedPassItemModel
import com.awscherb.cardkeeper.items.model.ItemModel
import com.awscherb.cardkeeper.items.model.PassItemModel
import com.awscherb.cardkeeper.items.model.ScannedCodeItemModel
import com.awscherb.cardkeeper.items.model.SortOptions
import com.awscherb.cardkeeper.items.repo.ItemsRepository
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.pkpass.model.isExpired
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val savedItemRepository: ItemsRepository
) : ViewModel() {

    companion object {
        private fun filterForOptions(filterOptions: FilterOptions): (ItemModel) -> Boolean =
            when (filterOptions) {
                FilterOptions.All -> { _ -> true }
                FilterOptions.QrCodes -> { item -> item is ScannedCodeItemModel }
                is FilterOptions.PassType -> { item ->
                    when (item) {
                        is PassItemModel -> item.passInfoType == filterOptions.type
                        is GroupedPassItemModel -> item.passes.any { it.passInfoType == filterOptions.type }
                        else -> false
                    }
                }
            }
    }

    val filter = MutableStateFlow<FilterOptions>(FilterOptions.All)

    val sort = MutableStateFlow<SortOptions>(SortOptions.Default)

    val showExpiredPasses = MutableStateFlow(false)

    val searchQuery = MutableStateFlow("")

    val items: Flow<List<ItemModel>> = searchQuery
        .flatMapLatest { savedItemRepository.listSavedItems(it) }
        .combine(showExpiredPasses) { items, showExpired ->
            items.filter {
                when {
                    it is PkPassModel && showExpired -> true
                    it is PkPassModel -> !it.isExpired()
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

    private val _reorderItems = MutableStateFlow<List<ItemModel>>(emptyList())
    val reorderItems: StateFlow<List<ItemModel>> = _reorderItems.asStateFlow()

    val reorderMode = MutableStateFlow(false)

    fun enterReorderMode(currentItems: List<ItemModel>) {
        _reorderItems.value = currentItems
        reorderMode.value = true
    }

    fun moveItem(fromIndex: Int, toIndex: Int) {
        _reorderItems.update {
            it.toMutableList().apply { add(toIndex, removeAt(fromIndex)) }
        }
    }

    fun cancelReorderMode() {
        reorderMode.value = false
        _reorderItems.value = emptyList()
    }

    fun saveReorder() {
        viewModelScope.launch {
            savedItemRepository.updateSortOrders(_reorderItems.value)
            reorderMode.value = false
            _reorderItems.value = emptyList()
        }
    }

}
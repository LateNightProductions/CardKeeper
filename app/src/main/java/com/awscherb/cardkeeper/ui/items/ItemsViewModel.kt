package com.awscherb.cardkeeper.ui.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awscherb.cardkeeper.data.model.SavedItem
import com.awscherb.cardkeeper.data.model.ScannedCodeModel
import com.awscherb.cardkeeper.data.repository.SavedItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class ItemsViewModel(
    private val savedItemRepository: SavedItemRepository
) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    val items: Flow<List<SavedItem>> = searchQuery
        .flatMapLatest { savedItemRepository.listSavedItems(it) }

    fun deleteCard(code: SavedItem) {
        viewModelScope.launch {
            savedItemRepository.deleteItem(code)
        }
    }
}
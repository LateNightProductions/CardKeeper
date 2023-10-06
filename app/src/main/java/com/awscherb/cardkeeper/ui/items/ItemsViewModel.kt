package com.awscherb.cardkeeper.ui.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awscherb.cardkeeper.pkpass.model.PkPassModel
import com.awscherb.cardkeeper.core.SavedItem
import com.awscherb.cardkeeper.data.repository.SavedItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
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

    fun deletePass(pass: PkPassModel) {
        viewModelScope.launch { savedItemRepository.deleteItem(pass) }
    }
}
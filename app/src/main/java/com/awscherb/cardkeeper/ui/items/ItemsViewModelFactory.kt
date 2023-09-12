package com.awscherb.cardkeeper.ui.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awscherb.cardkeeper.data.repository.SavedItemRepository
import javax.inject.Inject

class ItemsViewModelFactory @Inject constructor(
    private val savedItemRepository: SavedItemRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ItemsViewModel(savedItemRepository) as T
    }
}
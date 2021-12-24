package com.awscherb.cardkeeper.ui.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import javax.inject.Inject

class CardsViewModelFactory @Inject constructor(
    private val scannedCodeService: ScannedCodeService
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CardsViewModel(scannedCodeService) as T
    }
}
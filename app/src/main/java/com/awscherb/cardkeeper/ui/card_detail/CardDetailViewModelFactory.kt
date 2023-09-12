package com.awscherb.cardkeeper.ui.card_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awscherb.cardkeeper.barcode.service.ScannedCodeService
import javax.inject.Inject

class CardDetailViewModelFactory @Inject constructor(
    private val scannedCodeService: ScannedCodeService
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CardDetailViewModel(scannedCodeService) as T
    }
}
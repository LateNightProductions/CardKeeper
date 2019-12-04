package com.awscherb.cardkeeper.ui.card_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import javax.inject.Inject

class CardDetailViewModelFactory @Inject constructor(
    private val codeId: Int,
    private val scannedCodeService: ScannedCodeService
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CardDetailViewModel(codeId, scannedCodeService) as T
    }
}
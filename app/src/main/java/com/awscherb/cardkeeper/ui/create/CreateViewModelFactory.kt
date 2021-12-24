package com.awscherb.cardkeeper.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import javax.inject.Inject

class CreateViewModelFactory @Inject constructor(
    private val scannedCodeService: ScannedCodeService
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateViewModel(scannedCodeService) as T
    }
}
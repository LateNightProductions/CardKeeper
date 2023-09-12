package com.awscherb.cardkeeper.ui.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awscherb.cardkeeper.barcode.service.ScannedCodeService
import javax.inject.Inject

class ScanViewModelFactory @Inject constructor(
    private val scannedCodeService: ScannedCodeService
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ScanViewModel(scannedCodeService) as T
    }
}